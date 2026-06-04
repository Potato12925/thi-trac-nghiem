package com.tracnghiem.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

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
import com.tracnghiem.dto.LecturerDTO;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.service.AccountSettingsService;
import com.tracnghiem.service.LecturerService;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {

    private static final String INDEX_VIEW = "Lecturer/Index";
    private static final String REDIRECT_INDEX = "redirect:/lecturers";

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private AccountSettingsService accountSettingsService;

    private void prepareLecturerPage(ModelMap model, int page) {
        int pageSize = 10;

        model.addAttribute("lecturers", lecturerService.getLecturers(page, pageSize));

        long total = lecturerService.countLecturers();

        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    private String renderLecturerPage(ModelMap model, int page) {
        prepareLecturerPage(model, page);
        model.addAttribute("lecturerDTO", new LecturerDTO());
        return INDEX_VIEW;
    }

    @GetMapping("/home")
    public String home(ModelMap model, HttpSession session) {
        String lecturerId = (String) session.getAttribute("LOGIN_USER");
        Lecturer lecturer = lecturerId != null ? lecturerService.findLecturerById(lecturerId) : null;

        model.addAttribute("pageTitle", "Lecturer Homepage");
        model.addAttribute("lecturerProfile", lecturer);
        model.addAttribute("today", new Date());

        return "Lecturer/Home";
    }

    @GetMapping("/settings")
    public String settings(ModelMap model, HttpSession session) {
        String redirect = validateLecturerAccess(session);
        if (redirect != null) {
            return redirect;
        }

        populateSettingsPageModel(model, session, null, null);
        return "Lecturer/Settings";
    }

    @PostMapping("/settings/email/send-otp")
    public String sendEmailOtp(
            @Validated @ModelAttribute("changeEmailDTO") ChangeEmailDTO changeEmailDTO,
            BindingResult errors,
            ModelMap model,
            HttpSession session) {

        String redirect = validateLecturerAccess(session);
        if (redirect != null) {
            return redirect;
        }

        if (errors.hasErrors()) {
            populateSettingsPageModel(model, session, changeEmailDTO.getNewEmail(), null);
            return "Lecturer/Settings";
        }

        try {
            String lecturerId = (String) session.getAttribute("LOGIN_USER");
            String role = (String) session.getAttribute("ROLE");

            accountSettingsService.sendEmailChangeOtp(lecturerId, role, changeEmailDTO.getNewEmail());
            populateSettingsPageModel(model, session, changeEmailDTO.getNewEmail(), changeEmailDTO.getNewEmail());
            model.addAttribute("successMessage", "Mã OTP đã được gửi tới email mới của bạn");
            return "Lecturer/Settings";
        } catch (IllegalArgumentException | IllegalStateException ex) {
            populateSettingsPageModel(model, session, changeEmailDTO.getNewEmail(), null);
            model.addAttribute("errorMessage", ex.getMessage());
            return "Lecturer/Settings";
        }
    }

    @PostMapping("/settings/email/confirm")
    public String confirmEmailChange(
            @Validated @ModelAttribute("confirmEmailChangeDTO") ConfirmEmailChangeDTO confirmEmailChangeDTO,
            BindingResult errors,
            ModelMap model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String redirect = validateLecturerAccess(session);
        if (redirect != null) {
            return redirect;
        }

        if (errors.hasErrors()) {
            populateSettingsPageModel(model, session, confirmEmailChangeDTO.getNewEmail(), confirmEmailChangeDTO.getNewEmail());
            return "Lecturer/Settings";
        }

        try {
            String lecturerId = (String) session.getAttribute("LOGIN_USER");
            String role = (String) session.getAttribute("ROLE");

            accountSettingsService.confirmEmailChange(lecturerId, role, confirmEmailChangeDTO.getNewEmail(),
                    confirmEmailChangeDTO.getOtpCode());
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật email thành công");
            return "redirect:/lecturers/settings";
        } catch (IllegalArgumentException ex) {
            populateSettingsPageModel(model, session, confirmEmailChangeDTO.getNewEmail(), confirmEmailChangeDTO.getNewEmail());
            model.addAttribute("errorMessage", ex.getMessage());
            return "Lecturer/Settings";
        }
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") int page, ModelMap model) {
        prepareLecturerPage(model, page);
        model.addAttribute("lecturerDTO", new LecturerDTO());
        return INDEX_VIEW;
    }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            return renderLecturerPage(model, page);
        }

        try {
            lecturerService.addLecturer(lecturerDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return renderLecturerPage(model, page);
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
            ModelMap model) {
        if (errors.hasErrors()) {
            return renderLecturerPage(model, page);
        }

        try {
            lecturerService.updateLecturer(lecturerDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return renderLecturerPage(model, page);
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
            ModelMap model) {
        if (errors.hasErrors()) {
            return renderLecturerPage(model, page);
        }

        try {
            lecturerService.deleteLecturer(lecturerDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return renderLecturerPage(model, page);
        }
    }

    private void populateSettingsPageModel(ModelMap model, HttpSession session, String newEmail, String confirmEmail) {
        String lecturerId = (String) session.getAttribute("LOGIN_USER");
        Lecturer lecturer = lecturerService.findLecturerById(lecturerId);

        model.addAttribute("pageTitle", "Cài đặt giảng viên");
        model.addAttribute("lecturerProfile", lecturer);

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

    private String validateLecturerAccess(HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        if ("GIAOVIEN".equals(role)) {
            return null;
        }

        if ("SINHVIEN".equals(role)) {
            return "redirect:/students/home";
        }

        if ("PGV".equals(role)) {
            return "redirect:/admin/home";
        }

        return "redirect:/hello";
    }
}
