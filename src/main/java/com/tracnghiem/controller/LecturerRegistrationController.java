package com.tracnghiem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tracnghiem.dao.ClassroomDAO;
import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.dto.LecturerRegistrationDTO;
import com.tracnghiem.service.LecturerRegistrationService;
import com.tracnghiem.utils.RoleConstants;
import com.tracnghiem.utils.RoleNavigationUtils;

@Controller
@RequestMapping("/lecturer-registration")
public class LecturerRegistrationController {

	@Autowired
	private LecturerRegistrationService lecturerRegistrationService;

	@Autowired
	private ClassroomDAO classroomDAO;

	@Autowired
	private SubjectDAO subjectDAO;

	@Autowired
	private LecturerDAO lecturerDAO;

	private void prepareFormModel(ModelMap model, HttpSession session) {
		model.addAttribute("dsLop", classroomDAO.findAll());
		model.addAttribute("dsMonHoc", subjectDAO.findAll());

		String role = (String) session.getAttribute("ROLE");
		if (RoleConstants.PGV.equals(role)) {
			model.addAttribute("dsGiaoVien", lecturerDAO.findAll());
		}
	}

	private boolean isAuthorized(HttpSession session) {
		String role = (String) session.getAttribute("ROLE");
		return RoleConstants.PGV.equals(role) || RoleConstants.LECTURER.equals(role);
	}

	private String resolveUnauthorizedRedirect(HttpSession session) {
		String role = (String) session.getAttribute("ROLE");
		if (RoleNavigationUtils.isAuthenticatedRole(role)) {
			return RoleNavigationUtils.getHomeRedirect(role);
		}
		return "redirect:/auth/login";
	}

	@GetMapping
	public String Index(ModelMap model, HttpSession session) {
		if (!isAuthorized(session)) {
			return resolveUnauthorizedRedirect(session);
		}

		String role = (String) session.getAttribute("ROLE");
		String userMaGv = (String) session.getAttribute("LOGIN_USER");

		model.addAttribute("registrationDTO", new LecturerRegistrationDTO());
		prepareFormModel(model, session);
		model.addAttribute("registrations", lecturerRegistrationService.getRegistrations(userMaGv, role));

		return "LecturerRegistration/Index";
	}

	@PostMapping("/add")
	public String add(
			@Validated @ModelAttribute("registrationDTO") LecturerRegistrationDTO dto,
			BindingResult bindingResult,
			ModelMap model,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		String role = (String) session.getAttribute("ROLE");
		String userMaGv = (String) session.getAttribute("LOGIN_USER");

		if (!isAuthorized(session)) {
			return resolveUnauthorizedRedirect(session);
		}

		if (bindingResult.hasErrors()) {
			prepareFormModel(model, session);
			model.addAttribute("registrations", lecturerRegistrationService.getRegistrations(userMaGv, role));
			return "LecturerRegistration/Index";
		}

		try {
			lecturerRegistrationService.registerExam(dto, userMaGv, role);
			redirectAttributes.addFlashAttribute("successMessage", "ÄÄƒng kÃ½ thi thÃ nh cÃ´ng!");
			return "redirect:/lecturer-registration";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			prepareFormModel(model, session);
			model.addAttribute("registrations", lecturerRegistrationService.getRegistrations(userMaGv, role));
			return "LecturerRegistration/Index";
		}
	}

	@PostMapping("/update")
	public String update(
			@Validated @ModelAttribute("registrationDTO") LecturerRegistrationDTO dto,
			BindingResult bindingResult,
			ModelMap model,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		String role = (String) session.getAttribute("ROLE");
		String userMaGv = (String) session.getAttribute("LOGIN_USER");

		if (!isAuthorized(session)) {
			return resolveUnauthorizedRedirect(session);
		}

		if (bindingResult.hasErrors()) {
			prepareFormModel(model, session);
			model.addAttribute("registrations", lecturerRegistrationService.getRegistrations(userMaGv, role));
			return "LecturerRegistration/Index";
		}

		try {
			lecturerRegistrationService.updateExam(dto, userMaGv, role);
			redirectAttributes.addFlashAttribute("successMessage", "Cáº­p nháº­t lÆ°á»£t Ä‘Äƒng kÃ½ thi thÃ nh cÃ´ng!");
			return "redirect:/lecturer-registration";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			prepareFormModel(model, session);
			model.addAttribute("registrations", lecturerRegistrationService.getRegistrations(userMaGv, role));
			return "LecturerRegistration/Index";
		}
	}

	@DeleteMapping("/delete")
	public String delete(
			@ModelAttribute("registrationDTO") LecturerRegistrationDTO dto,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		String role = (String) session.getAttribute("ROLE");
		String userMaGv = (String) session.getAttribute("LOGIN_USER");

		if (!isAuthorized(session)) {
			return resolveUnauthorizedRedirect(session);
		}

		try {
			lecturerRegistrationService.deleteExam(dto.getClassId(), dto.getSubjectId(), dto.getTryNumber(), userMaGv,
					role);
			redirectAttributes.addFlashAttribute("successMessage", "XÃ³a lÆ°á»£t Ä‘Äƒng kÃ½ thi thÃ nh cÃ´ng!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}

		return "redirect:/lecturer-registration";
	}
}
