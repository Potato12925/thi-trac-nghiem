package com.tracnghiem.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tracnghiem.dto.ChangeEmailDTO;
import com.tracnghiem.dto.ChangePasswordDTO;
import com.tracnghiem.dto.ConfirmEmailChangeDTO;
import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.dto.StudentDashboardDTO;
import com.tracnghiem.dto.UpcomingExamDTO;
import com.tracnghiem.entity.Student;
import com.tracnghiem.service.ClassroomService;
import com.tracnghiem.service.AccountSettingsService;
import com.tracnghiem.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String INDEX_VIEW = "Student/Index";
    private static final String REDIRECT_INDEX = "redirect:/students";

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private AccountSettingsService accountSettingsService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "filterClassId") String filterClassId,
            ModelMap model) {
        populateIndexPageModel(model, page, keyword, filterClassId);
        model.addAttribute("studentDTO", new StudentDTO());
        return INDEX_VIEW;
    }

    @GetMapping("/home")
    public String home(ModelMap model, HttpSession session) {
        String redirect = validateStudentAccess(session);
        if (redirect != null) {
            return redirect;
        }

        String studentId = normalize((String) session.getAttribute("LOGIN_USER"));
        Date today = new Date();        Student student = studentService.getDashboardStudentProfile(studentId);
        StudentDashboardDTO dashboard = studentService.getStudentDashboardStats(student, today);
        java.util.List<UpcomingExamDTO> upcomingExams = studentService.getUpcomingExams(student, today);

        model.addAttribute("pageTitle", "Student Home");
        model.addAttribute("studentProfile", student);
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("upcomingExams", upcomingExams);
        model.addAttribute("today", today);

        return "Student/Home";
    }

    @GetMapping("/settings")
    public String settings(ModelMap model, HttpSession session) {
        String redirect = validateStudentAccess(session);
        if (redirect != null) {
            return redirect;
        }

        populateSettingsPageModel(model, session, null, null);
        return "Student/Settings";
    }

    @PostMapping("/settings/email/send-otp")
    public String sendEmailOtp(
            @Validated @ModelAttribute("changeEmailDTO") ChangeEmailDTO changeEmailDTO,
            BindingResult errors,
            ModelMap model,
            HttpSession session) {

        String redirect = validateStudentAccess(session);
        if (redirect != null) {
            return redirect;
        }

        if (errors.hasErrors()) {
            populateSettingsPageModel(model, session, changeEmailDTO.getNewEmail(), null);
            return "Student/Settings";
        }

        try {
            String studentId = (String) session.getAttribute("LOGIN_USER");
            String role = (String) session.getAttribute("ROLE");

            accountSettingsService.sendEmailChangeOtp(studentId, role, changeEmailDTO.getNewEmail());
            populateSettingsPageModel(model, session, changeEmailDTO.getNewEmail(), changeEmailDTO.getNewEmail());
            model.addAttribute("successMessage", "Mã OTP đã được gửi tới email mới của bạn");
            return "Student/Settings";
        } catch (IllegalArgumentException | IllegalStateException ex) {
            populateSettingsPageModel(model, session, changeEmailDTO.getNewEmail(), null);
            model.addAttribute("errorMessage", ex.getMessage());
            return "Student/Settings";
        }
    }

    @PostMapping("/settings/email/confirm")
    public String confirmEmailChange(
            @Validated @ModelAttribute("confirmEmailChangeDTO") ConfirmEmailChangeDTO confirmEmailChangeDTO,
            BindingResult errors,
            ModelMap model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String redirect = validateStudentAccess(session);
        if (redirect != null) {
            return redirect;
        }

        if (errors.hasErrors()) {
            populateSettingsPageModel(model, session, confirmEmailChangeDTO.getNewEmail(), confirmEmailChangeDTO.getNewEmail());
            return "Student/Settings";
        }

        try {
            String studentId = (String) session.getAttribute("LOGIN_USER");
            String role = (String) session.getAttribute("ROLE");

            accountSettingsService.confirmEmailChange(studentId, role, confirmEmailChangeDTO.getNewEmail(),
                    confirmEmailChangeDTO.getOtpCode());
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật email thành công");
            return "redirect:/students/settings";
        } catch (IllegalArgumentException ex) {
            populateSettingsPageModel(model, session, confirmEmailChangeDTO.getNewEmail(), confirmEmailChangeDTO.getNewEmail());
            model.addAttribute("errorMessage", ex.getMessage());
            return "Student/Settings";
        }
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "filterClassId") String filterClassId,
            @Validated @ModelAttribute("studentDTO") StudentDTO studentDTO,
            BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }

        try {
            studentService.addStudentWithAccount(studentDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/update")
    public String updateStudent(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "filterClassId") String filterClassId,
            @Validated @ModelAttribute("studentDTO") StudentDTO studentDTO,
            BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }

        try {
            studentService.updateStudent(studentDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "filterClassId") String filterClassId,
            @Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
            BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }

        try {
            studentService.deleteStudent(studentDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }
    }

    private void populateIndexPageModel(ModelMap model, int page, String keyword, String filterClassId) {
        int pageSize = 10;

        model.addAttribute("studentDTO", new StudentDTO());
        model.addAttribute("students", studentService.getStudents(page, pageSize, keyword, filterClassId));
        model.addAttribute("classrooms", classroomService.getAllClassrooms());
        model.addAttribute("keyword", keyword);
        model.addAttribute("classId", filterClassId);

        long total = studentService.countStudents(keyword, filterClassId);
        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    private String buildQuery(int page, String keyword, String filterClassId) {
        StringBuilder query = new StringBuilder("?page=").append(page);
        if (keyword != null && !keyword.trim().isEmpty()) {
            query.append("&keyword=").append(URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8));
        }
        if (filterClassId != null && !filterClassId.trim().isEmpty()) {
            query.append("&filterClassId=").append(URLEncoder.encode(filterClassId.trim(), StandardCharsets.UTF_8));
        }
        return query.toString();
    }

    private void populateSettingsPageModel(ModelMap model, HttpSession session, String newEmail, String confirmEmail) {
        String studentId = (String) session.getAttribute("LOGIN_USER");
        Student student = studentService.getStudentById(studentId);

        model.addAttribute("pageTitle", "Cài đặt sinh viên");
        model.addAttribute("studentProfile", student);

        if (!model.containsAttribute("changeEmailDTO")) {
            ChangeEmailDTO emailDTO = new ChangeEmailDTO();
            emailDTO.setNewEmail(newEmail);
            model.addAttribute("changeEmailDTO", emailDTO);
        }

        if (!model.containsAttribute("confirmEmailChangeDTO")) {
            ConfirmEmailChangeDTO confirmEmailChangeDTO = new ConfirmEmailChangeDTO();
            confirmEmailChangeDTO.setNewEmail(confirmEmail);
            model.addAttribute("confirmEmailChangeDTO", confirmEmailChangeDTO);
        }

        if (!model.containsAttribute("changePasswordDTO")) {
            model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        }
    }

    private String validateStudentAccess(HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        if ("SINHVIEN".equals(role)) {
            return null;
        }

        if ("GIAOVIEN".equals(role)) {
            return "redirect:/lecturers/home";
        }

        if ("PGV".equals(role)) {
            return "redirect:/admin/home";
        }

        return "redirect:/hello";
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
