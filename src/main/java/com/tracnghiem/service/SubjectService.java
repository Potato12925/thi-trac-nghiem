package com.tracnghiem.service;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dao.TeacherRegistrationDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.ScoreDAO;
import com.tracnghiem.entity.Subject;

/**
 * Service layer for Subject (MonHoc) management.
 * Handles business logic, validation, CRUD operations, and undo functionality.
 * 
 * Flow: Controller -> Service -> DAO -> Database
 */
@Service
public class SubjectService {

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private TeacherRegistrationDAO teacherRegistrationDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private ExamDAO examDAO;

    @Autowired
    private ScoreDAO scoreDAO;

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
        Subject subject = subjectDAO.findById(maMH.trim());
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

        List<Subject> subjects = subjectDAO.findByKeyword(tenMH.trim());
        for (Subject subject : subjects) {
            if (subject.getSubjectName().equalsIgnoreCase(tenMH.trim())) {
                if (excludeMaMH == null || !subject.getSubjectId().equals(excludeMaMH)) {
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
        long teacherRegistrationCount = subjectDAO.countGiaoVienDangKyByMaMH(normalized);
        if (teacherRegistrationCount > 0) {
            return "Không thể xóa môn học. Có " + teacherRegistrationCount
                    + " giáo viên đã đăng ký môn học này";
        }

        // Check BoDe (Question bank)
        long questionCount = subjectDAO.countBoDeByMaMH(normalized);
        if (questionCount > 0) {
            return "Không thể xóa môn học. Có " + questionCount + " câu hỏi thuộc môn học này";
        }

        // Check BaiThi (Exam)
        long examCount = subjectDAO.countBaiThiByMaMH(normalized);
        if (examCount > 0) {
            return "Không thể xóa môn học. Có " + examCount + " bài thi của môn học này";
        }

        // Check BangDiem (Grade)
        long scoreCount = subjectDAO.countBangDiemByMaMH(normalized);
        if (scoreCount > 0) {
            return "Không thể xóa môn học. Có " + scoreCount + " bảng điểm của môn học này";
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

        String normalizedSubjectCode = maMH.trim();
        String normalizedSubjectName = tenMH.trim();

        // Check duplicate code
        if (isMaMHExists(normalizedSubjectCode)) {
            return "Mã môn học '" + normalizedSubjectCode + "' đã tồn tại";
        }

        // Check duplicate name
        if (isTenMHExists(normalizedSubjectName, null)) {
            return "Tên môn học '" + normalizedSubjectName + "' đã tồn tại";
        }

        // Create new subject
        Subject subject = new Subject();
        subject.setSubjectId(normalizedSubjectCode);
        subject.setSubjectName(normalizedSubjectName);

        // Store to database
        subjectDAO.create(subject);

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

        String normalizedSubjectCode = maMH.trim();

        // Find subject to update
        Subject subject = subjectDAO.findById(normalizedSubjectCode);
        if (subject == null) {
            return "Môn học không tồn tại";
        }

        // Validate new name
        String tenMHValidation = validateTenMH(tenMH);
        if (tenMHValidation != null) {
            return tenMHValidation;
        }

        String normalizedSubjectName = tenMH.trim();

        // Check if new name is already used by another subject
        if (isTenMHExists(normalizedSubjectName, normalizedSubjectCode)) {
            return "Tên môn học '" + normalizedSubjectName + "' đã tồn tại";
        }

        // Store old data for undo
        Operation operation = new Operation(OP_UPDATE, new Subject());
        operation.getOldEntity().setSubjectId(subject.getSubjectId());
        operation.getOldEntity().setSubjectName(subject.getSubjectName());

        // Update subject
        subject.setSubjectName(normalizedSubjectName);
        subjectDAO.update(subject);

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

        String normalizedSubjectCode = maMH.trim();

        // Find subject to delete
        Subject subject = subjectDAO.findById(normalizedSubjectCode);
        if (subject == null) {
            return "Môn học không tồn tại";
        }

        // Validate delete constraints
        String constraintError = validateDeleteConstraint(normalizedSubjectCode);
        if (constraintError != null) {
            return constraintError;
        }

        // Store subject data for undo
        Operation operation = new Operation(OP_DELETE, subject);

        // Delete from database
        subjectDAO.delete(subject);

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
            return subjectDAO.findAll();
        }

        return subjectDAO.findByKeyword(keyword.trim());
    }

    /**
     * Gets all subjects.
     * 
     * @return List of all subjects
     */
    public List<Subject> getAllSubjects() {
        return subjectDAO.findAll();
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
        return subjectDAO.findById(maMH.trim());
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
                    subjectDAO.delete(operation.getEntity());
                    return "Phục hồi: Xóa môn học '" + operation.getEntity().getSubjectId() + "'";

                case OP_UPDATE:
                    // Undo UPDATE by restoring old values
                    Subject currentSubject = subjectDAO.findById(operation.getOldEntity().getSubjectId());
                    if (currentSubject != null) {
                        currentSubject.setSubjectName(operation.getOldEntity().getSubjectName());
                        subjectDAO.update(currentSubject);
                    }
                    return "Phục hồi: Cập nhật môn học '" + operation.getOldEntity().getSubjectId() + "'";

                case OP_DELETE:
                    // Undo DELETE by creating again
                    subjectDAO.create(operation.getEntity());
                    return "Phục hồi: Thêm môn học '" + operation.getEntity().getSubjectId() + "'";

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
