package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tracnghiem.entity.Subject;
import com.tracnghiem.service.MonHocService;

/**
 * Controller for Subject (MonHoc) management.
 * Handles HTTP requests and delegates business logic to MonHocService.
 * 
 * Responsibilities:
 * - Handle request/response
 * - Call appropriate service methods
 * - Prepare data for JSP view
 * 
 * All business logic is delegated to MonHocService
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private MonHocService monHocService;

    /**
     * Displays subject list with optional search.
     * GET /subject
     * 
     * Query parameters:
     * - search: keyword to search in maMH or tenMH
     * - maMH: subject code to edit (shows form with data)
     * 
     * @param search Search keyword (optional)
     * @param maMH Subject code to load for editing (optional)
     * @param model Spring model for passing data to view
     * @return View name: Subject/Index
     */
    @GetMapping
    public String index(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "maMH", required = false) String maMH,
            Model model) {

        // Search or get all subjects
        List<Subject> subjects;
        if (search != null && !search.trim().isEmpty()) {
            subjects = monHocService.searchSubjects(search.trim());
        } else {
            subjects = monHocService.getAllSubjects();
        }

        // Load subject for editing if maMH provided
        Subject subject = null;
        if (maMH != null && !maMH.trim().isEmpty()) {
            subject = monHocService.getSubjectById(maMH.trim());
        }
        if (subject == null) {
            subject = new Subject(); // Empty for new entry
        }

        // Pass data to view
        model.addAttribute("subjects", subjects);
        model.addAttribute("subject", subject);
        model.addAttribute("search", search);
        model.addAttribute("canUndo", monHocService.canUndo());

        return "Subject/Index";
    }

    /**
     * Creates or updates a subject.
     * POST /subject/save
     * 
     * Request parameters:
     * - maMH: subject code (5 characters)
     * - tenMH: subject name (max 40 characters)
     * 
     * Validation handled by MonHocService:
     * - maMH: not empty, exactly 5 chars, no duplicates
     * - tenMH: not empty, no duplicates
     * 
     * @param maMH Subject code
     * @param tenMH Subject name
     * @param redirectAttributes For passing error/success messages
     * @return Redirect to /subject view
     */
    @PostMapping("/save")
    public String save(
            @RequestParam("maMH") String maMH,
            @RequestParam("tenMH") String tenMH,
            RedirectAttributes redirectAttributes) {

        if (maMH == null || maMH.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mã môn học không được rỗng");
            return "redirect:/subject";
        }

        String normalized_maMH = maMH.trim();

        // Check if this is create or update
        Subject existingSubject = monHocService.getSubjectById(normalized_maMH);

        String errorMessage;
        if (existingSubject == null) {
            // CREATE operation
            errorMessage = monHocService.createSubject(normalized_maMH, tenMH);
            if (errorMessage == null) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "Thêm môn học '" + normalized_maMH + "' thành công");
            }
        } else {
            // UPDATE operation
            errorMessage = monHocService.updateSubject(normalized_maMH, tenMH);
            if (errorMessage == null) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "Cập nhật môn học '" + normalized_maMH + "' thành công");
            }
        }

        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            redirectAttributes.addAttribute("maMH", normalized_maMH);
        }

        return "redirect:/subject" + (errorMessage == null ? "" : "?maMH=" + normalized_maMH);
    }

    /**
     * Saves a batch of pending subjects added on the client side.
     * POST /subject/save-batch
     * 
     * @param pendingMaMH Array of subject codes
     * @param pendingTenMH Array of subject names
     * @param redirectAttributes For passing success/error messages
     * @return Redirect to /subject view
     */
    @PostMapping("/save-batch")
    public String saveBatch(
            @RequestParam(value = "pendingMaMH", required = false) String[] pendingMaMH,
            @RequestParam(value = "pendingTenMH", required = false) String[] pendingTenMH,
            RedirectAttributes redirectAttributes) {

        if (pendingMaMH == null || pendingTenMH == null || pendingMaMH.length == 0 || pendingMaMH.length != pendingTenMH.length) {
            redirectAttributes.addFlashAttribute("infoMessage", "Không có môn học tạm để lưu.");
            return "redirect:/subject";
        }

        int savedCount = 0;
        for (int i = 0; i < pendingMaMH.length; i++) {
            String maMH = pendingMaMH[i] != null ? pendingMaMH[i].trim() : null;
            String tenMH = pendingTenMH[i] != null ? pendingTenMH[i].trim() : null;

            if (maMH == null || maMH.isEmpty() || tenMH == null || tenMH.isEmpty()) {
                continue;
            }

            String errorMessage = monHocService.createSubject(maMH, tenMH);
            if (errorMessage != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu môn học '" + maMH + "': " + errorMessage);
                return "redirect:/subject";
            }
            savedCount++;
        }

        if (savedCount > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Đã lưu " + savedCount + " môn học vào cơ sở dữ liệu.");
        } else {
            redirectAttributes.addFlashAttribute("infoMessage", "Không có môn học hợp lệ để lưu.");
        }

        return "redirect:/subject";
    }

    /**
     * Deletes a subject with foreign key constraint validation.
     * POST /subject/delete
     * 
     * Validation:
     * - Subject must exist
     * - No references in GiaoVienDangKy, BoDe, BaiThi, BangDiem
     * 
     * @param maMH Subject code to delete
     * @param redirectAttributes For passing error/success messages
     * @return Redirect to /subject view
     */
    @PostMapping("/delete")
    public String delete(
            @RequestParam("maMH") String maMH,
            RedirectAttributes redirectAttributes) {

        if (maMH == null || maMH.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mã môn học không hợp lệ");
            return "redirect:/subject";
        }

        String normalized_maMH = maMH.trim();
        String errorMessage = monHocService.deleteSubject(normalized_maMH);

        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        } else {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Xóa môn học '" + normalized_maMH + "' thành công");
        }

        return "redirect:/subject";
    }

    /**
     * Performs undo operation for the last action.
     * POST /subject/undo
     * 
     * Supported undo operations:
     * - CREATE -> DELETE the created subject
     * - UPDATE -> Restore to previous name
     * - DELETE -> Recreate the deleted subject
     * 
     * @param redirectAttributes For passing undo message
     * @return Redirect to /subject view
     */
    @PostMapping("/undo")
    public String undo(RedirectAttributes redirectAttributes) {
        if (!monHocService.canUndo()) {
            redirectAttributes.addFlashAttribute("infoMessage", "Không có thao tác nào để phục hồi");
            return "redirect:/subject";
        }

        String undoMessage = monHocService.undoLastOperation();
        if (undoMessage != null) {
            redirectAttributes.addFlashAttribute("successMessage", undoMessage);
        }

        return "redirect:/subject";
    }
}
