package com.tracnghiem.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.ScoreDAO;
import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dao.TeacherRegistrationDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.entity.Subject;

/**
 * Service layer for Subject (MonHoc) management.
 * Handles business logic, validation, CRUD operations, and undo functionality.
 * 
 * Flow: Controller -> Service -> DAO -> Database
 */
@Service
public class MonHocService {

    @Autowired
    private SubjectDAO monHocDAO;

    @Autowired
    private TeacherRegistrationDAO giaoVienDangKyDAO;

    @Autowired
    private QuestionDAO boDeDAO;

    @Autowired
    private ExamDAO baiThiDAO;

    @Autowired
    private ScoreDAO bangDiemDAO;

    // Stack for storing operation history for undo functionality
    private Stack<Operation> operationHistory = new Stack<>();

    // Constants for operation types
    private static final String OP_CREATE = "CREATE";
    private static final String OP_UPDATE = "UPDATE";
    private static final String OP_DELETE = "DELETE";

    // ==================== VALIDATION METHODS ====================

    /**
     * Validates subject code (maMH)
     * Rules:
     * - Not null or empty
     * - Exactly 5 characters
     * - Contains only alphanumeric characters
     * 
     * @param maMH Subject code
     * @return Error message, or null if valid
     */
    public String validateMaMH(String maMH) {
        if (maMH == null || maMH.trim().isEmpty()) {
            return "Mã môn học không được rỗng";
        }

        String normalized = maMH.trim();

        if (normalized.length() != 5) {
            return "Mã môn học phải đúng 5 ký tự";
        }

        if (!normalized.matches("^[A-Za-z0-9]+$")) {
            return "Mã môn học chỉ chứa ký tự chữ và số";
        }

        return null;
    }

    /**
     * Validates subject name (tenMH)
     * Rules:
     * - Not null or empty
     * - Maximum 40 characters
     * 
     * @param tenMH Subject name
     * @return Error message, or null if valid
     */
    public String validateTenMH(String tenMH) {
        if (tenMH == null || tenMH.trim().isEmpty()) {
            return "Tên môn học không được rỗng";
        }

        String normalized = tenMH.trim();

        if (normalized.length() > 40) {
            return "Tên môn học không được vượt quá 40 ký tự";
        }

        return null;
    }

    /**
     * Checks if subject code already exists in database
     * 
     * @param maMH Subject code
     * @return true if exists, false otherwise
     */
    public boolean isMaMHExists(String maMH) {
        if (maMH == null || maMH.trim().isEmpty()) {
            return false;
        }
        Subject subject = monHocDAO.findById(maMH.trim());
        return subject != null;
    }

    /**
     * Checks if subject name already exists in database (excluding specified subject)
     * 
     * @param tenMH Subject name
     * @param excludeMaMH Subject code to exclude (for update scenario)
     * @return true if exists, false otherwise
     */
    public boolean isTenMHExists(String tenMH, String excludeMaMH) {
        if (tenMH == null || tenMH.trim().isEmpty()) {
            return false;
        }

        List<Subject> subjects = monHocDAO.findByKeyword(tenMH.trim());
        for (Subject subject : subjects) {
            if (subject.getTenMH().equalsIgnoreCase(tenMH.trim())) {
                if (excludeMaMH == null || !subject.getMaMH().equals(excludeMaMH)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if subject is used in other tables (foreign key constraint validation)
     * Tables that reference MonHoc:
     * - GiaoVienDangKy (Subject registration)
     * - BoDe (Question bank)
     * - BaiThi (Exam)
     * - BangDiem (Grade)
     * 
     * @param maMH Subject code
     * @return Error message if subject is used, null otherwise
     */
    public String validateDeleteConstraint(String maMH) {
        if (maMH == null || maMH.trim().isEmpty()) {
            return "Mã môn học không hợp lệ";
        }

        String normalized = maMH.trim();

        // Check GiaoVienDangKy (Subject registration)
        long giaoVienDangKyCount = monHocDAO.countGiaoVienDangKyByMaMH(normalized);
        if (giaoVienDangKyCount > 0) {
            return "Không thể xóa môn học. Có " + giaoVienDangKyCount
                    + " giáo viên đã đăng ký môn học này";
        }

        // Check BoDe (Question bank)
        long boDeCount = monHocDAO.countBoDeByMaMH(normalized);
        if (boDeCount > 0) {
            return "Không thể xóa môn học. Có " + boDeCount + " câu hỏi thuộc môn học này";
        }

        // Check BaiThi (Exam)
        long baiThiCount = monHocDAO.countBaiThiByMaMH(normalized);
        if (baiThiCount > 0) {
            return "Không thể xóa môn học. Có " + baiThiCount + " bài thi của môn học này";
        }

        // Check BangDiem (Grade)
        long bangDiemCount = monHocDAO.countBangDiemByMaMH(normalized);
        if (bangDiemCount > 0) {
            return "Không thể xóa môn học. Có " + bangDiemCount + " bảng điểm của môn học này";
        }

        return null;
    }

    // ==================== CRUD OPERATIONS ====================

    /**
     * Creates a new subject with validation.
     * 
     * Process:
     * 1. Validate maMH
     * 2. Validate tenMH
     * 3. Check if maMH already exists
     * 4. Check if tenMH already exists
     * 5. Create subject
     * 6. Save operation to history for undo
     * 
     * @param maMH Subject code
     * @param tenMH Subject name
     * @return Error message if validation fails, null if success
     */
    @Transactional
    public String createSubject(String maMH, String tenMH) {
        // Validation
        String maMHValidation = validateMaMH(maMH);
        if (maMHValidation != null) {
            return maMHValidation;
        }

        String tenMHValidation = validateTenMH(tenMH);
        if (tenMHValidation != null) {
            return tenMHValidation;
        }

        String normalized_maMH = maMH.trim();
        String normalized_tenMH = tenMH.trim();

        // Check duplicate code
        if (isMaMHExists(normalized_maMH)) {
            return "Mã môn học '" + normalized_maMH + "' đã tồn tại";
        }

        // Check duplicate name
        if (isTenMHExists(normalized_tenMH, null)) {
            return "Tên môn học '" + normalized_tenMH + "' đã tồn tại";
        }

        // Create new subject
        Subject subject = new Subject();
        subject.setMaMH(normalized_maMH);
        subject.setTenMH(normalized_tenMH);

        // Store to database
        monHocDAO.create(subject);

        // Record operation for undo
        Operation operation = new Operation(OP_CREATE, subject);
        operationHistory.push(operation);

        return null; // null indicates success
    }

    /**
     * Updates an existing subject with validation.
     * 
     * Process:
     * 1. Check if subject exists
     * 2. Validate tenMH
     * 3. Check if new tenMH already exists (excluding current subject)
     * 4. Update subject
     * 5. Save operation to history for undo
     * 
     * @param maMH Subject code (primary key, cannot change)
     * @param tenMH New subject name
     * @return Error message if validation fails, null if success
     */
    @Transactional
    public String updateSubject(String maMH, String tenMH) {
        if (maMH == null || maMH.trim().isEmpty()) {
            return "Mã môn học không hợp lệ";
        }

        String normalized_maMH = maMH.trim();

        // Find subject to update
        Subject subject = monHocDAO.findById(normalized_maMH);
        if (subject == null) {
            return "Môn học không tồn tại";
        }

        // Validate new name
        String tenMHValidation = validateTenMH(tenMH);
        if (tenMHValidation != null) {
            return tenMHValidation;
        }

        String normalized_tenMH = tenMH.trim();

        // Check if new name is already used by another subject
        if (isTenMHExists(normalized_tenMH, normalized_maMH)) {
            return "Tên môn học '" + normalized_tenMH + "' đã tồn tại";
        }

        // Store old data for undo
        Operation operation = new Operation(OP_UPDATE, new Subject());
        operation.getOldEntity().setMaMH(subject.getMaMH());
        operation.getOldEntity().setTenMH(subject.getTenMH());

        // Update subject
        subject.setTenMH(normalized_tenMH);
        monHocDAO.update(subject);

        // Record operation for undo
        operationHistory.push(operation);

        return null; // null indicates success
    }

    /**
     * Deletes a subject with constraint validation.
     * 
     * Process:
     * 1. Validate if subject exists
     * 2. Check foreign key constraints
     * 3. Delete subject
     * 4. Save operation to history for undo
     * 
     * @param maMH Subject code
     * @return Error message if validation fails or constraints violated, null if success
     */
    @Transactional
    public String deleteSubject(String maMH) {
        if (maMH == null || maMH.trim().isEmpty()) {
            return "Mã môn học không hợp lệ";
        }

        String normalized_maMH = maMH.trim();

        // Find subject to delete
        Subject subject = monHocDAO.findById(normalized_maMH);
        if (subject == null) {
            return "Môn học không tồn tại";
        }

        // Validate delete constraints
        String constraintError = validateDeleteConstraint(normalized_maMH);
        if (constraintError != null) {
            return constraintError;
        }

        // Store subject data for undo
        Operation operation = new Operation(OP_DELETE, subject);

        // Delete from database
        monHocDAO.delete(subject);

        // Record operation for undo
        operationHistory.push(operation);

        return null; // null indicates success
    }

    // ==================== SEARCH OPERATIONS ====================

    /**
     * Searches subjects by keyword.
     * Searches in both maMH and tenMH fields.
     * 
     * @param keyword Search keyword
     * @return List of matching subjects
     */
    public List<Subject> searchSubjects(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return monHocDAO.findAll();
        }

        return monHocDAO.findByKeyword(keyword.trim());
    }

    /**
     * Gets all subjects.
     * 
     * @return List of all subjects
     */
    public List<Subject> getAllSubjects() {
        return monHocDAO.findAll();
    }

    /**
     * Gets subject by ID.
     * 
     * @param maMH Subject code
     * @return Subject entity or null if not found
     */
    public Subject getSubjectById(String maMH) {
        if (maMH == null || maMH.trim().isEmpty()) {
            return null;
        }
        return monHocDAO.findById(maMH.trim());
    }

    // ==================== UNDO FUNCTIONALITY ====================

    /**
     * Performs undo operation for the last action.
     * Supported undo operations: CREATE, UPDATE, DELETE
     * 
     * Process:
     * 1. Pop last operation from stack
     * 2. Reverse the operation:
     *    - CREATE -> DELETE
     *    - UPDATE -> UPDATE to old values
     *    - DELETE -> CREATE
     * 3. Apply reversed operation to database
     * 
     * @return Error message if undo fails, null if success
     */
    @Transactional
    public String undoLastOperation() {
        if (operationHistory.isEmpty()) {
            return "Không có thao tác nào để phục hồi";
        }

        Operation operation = operationHistory.pop();

        try {
            switch (operation.getOperationType()) {
                case OP_CREATE:
                    // Undo CREATE by deleting
                    monHocDAO.delete(operation.getEntity());
                    return "Phục hồi: Xóa môn học '" + operation.getEntity().getMaMH() + "'";

                case OP_UPDATE:
                    // Undo UPDATE by restoring old values
                    Subject currentSubject = monHocDAO.findById(operation.getOldEntity().getMaMH());
                    if (currentSubject != null) {
                        currentSubject.setTenMH(operation.getOldEntity().getTenMH());
                        monHocDAO.update(currentSubject);
                    }
                    return "Phục hồi: Cập nhật môn học '" + operation.getOldEntity().getMaMH() + "'";

                case OP_DELETE:
                    // Undo DELETE by creating again
                    monHocDAO.create(operation.getEntity());
                    return "Phục hồi: Thêm môn học '" + operation.getEntity().getMaMH() + "'";

                default:
                    return "Loại thao tác không hợp lệ";
            }
        } catch (Exception e) {
            return "Lỗi khi phục hồi: " + e.getMessage();
        }
    }

    /**
     * Checks if there are operations in history for undo.
     * 
     * @return true if undo is available, false otherwise
     */
    public boolean canUndo() {
        return !operationHistory.isEmpty();
    }

    /**
     * Gets the count of operations in history.
     * 
     * @return Number of operations that can be undone
     */
    public int getHistorySize() {
        return operationHistory.size();
    }

    /**
     * Clears operation history.
     * Used for session cleanup.
     */
    public void clearHistory() {
        operationHistory.clear();
    }

    // ==================== INNER CLASS: OPERATION ====================

    /**
     * Represents an operation for undo/redo functionality.
     * Stores:
     * - Type of operation (CREATE, UPDATE, DELETE)
     * - Entity data after operation
     * - Old entity data (for UPDATE)
     */
    private class Operation implements Serializable {
        private static final long serialVersionUID = 1L;

        private String operationType;
        private Subject entity;
        private Subject oldEntity;

        Operation(String operationType, Subject entity) {
            this.operationType = operationType;
            this.entity = entity;
            this.oldEntity = new Subject();
        }

        String getOperationType() {
            return operationType;
        }

        Subject getEntity() {
            return entity;
        }

        Subject getOldEntity() {
            return oldEntity;
        }
    }
}
