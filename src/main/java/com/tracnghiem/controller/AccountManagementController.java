package com.tracnghiem.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tracnghiem.dto.AccountFilterDTO;
import com.tracnghiem.dto.AccountManagementItemDTO;
import com.tracnghiem.dto.AdminResetPasswordDTO;
import com.tracnghiem.dto.CreateLecturerAccountDTO;
import com.tracnghiem.dto.CreatePgvAccountDTO;
import com.tracnghiem.dto.UpdateAccountRoleDTO;
import com.tracnghiem.entity.Account;
import com.tracnghiem.service.AccountManagementService;

@Controller
@RequestMapping("/admin/accounts")
public class AccountManagementController {

	private static final String INDEX_VIEW = "Account/Management";

	@Autowired
	private AccountManagementService accountManagementService;

	@GetMapping
	public String index(@ModelAttribute("accountFilter") AccountFilterDTO accountFilter, ModelMap model,
			HttpSession session) {
		String redirect = validateAdminAccess(session);
		if (redirect != null) {
			return redirect;
		}

		populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
		return INDEX_VIEW;
	}

	@PostMapping("/create-pgv")
	public String createPgv(@Valid @ModelAttribute("createPgvAccountDTO") CreatePgvAccountDTO createPgvAccountDTO,
			BindingResult errors, @ModelAttribute("accountFilter") AccountFilterDTO accountFilter,
			@RequestParam(value = "filterRole", required = false) String filterRole, ModelMap model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		String redirect = validateAdminAccess(session);
		if (redirect != null) {
			return redirect;
		}

		mergeFilterRole(accountFilter, filterRole);

		if (errors.hasErrors()) {
			populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
			return INDEX_VIEW;
		}

		try {
			accountManagementService.createPgvAccount(createPgvAccountDTO);
			redirectAttributes.addFlashAttribute("successMessage", "Tạo tài khoản PGV thành công");
			appendFilterAttributes(redirectAttributes, accountFilter);
			return "redirect:/admin/accounts";
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
			return INDEX_VIEW;
		}
	}

	@PostMapping("/create-lecturer")
	public String createLecturer(
			@Valid @ModelAttribute("createLecturerAccountDTO") CreateLecturerAccountDTO createLecturerAccountDTO,
			BindingResult errors, @ModelAttribute("accountFilter") AccountFilterDTO accountFilter,
			@RequestParam(value = "filterRole", required = false) String filterRole, ModelMap model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		String redirect = validateAdminAccess(session);
		if (redirect != null) {
			return redirect;
		}

		mergeFilterRole(accountFilter, filterRole);

		if (errors.hasErrors()) {
			populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
			return INDEX_VIEW;
		}

		try {
			accountManagementService.createLecturerAccount(createLecturerAccountDTO);
			redirectAttributes.addFlashAttribute("successMessage", "Tạo tài khoản giảng viên thành công");
			appendFilterAttributes(redirectAttributes, accountFilter);
			return "redirect:/admin/accounts";
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
			return INDEX_VIEW;
		}
	}

	@PostMapping("/reset-password")
	public String resetPassword(
			@Valid @ModelAttribute("adminResetPasswordDTO") AdminResetPasswordDTO adminResetPasswordDTO,
			BindingResult errors, @ModelAttribute("accountFilter") AccountFilterDTO accountFilter,
			@RequestParam(value = "filterRole", required = false) String filterRole, ModelMap model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		String redirect = validateAdminAccess(session);
		if (redirect != null) {
			return redirect;
		}

		mergeFilterRole(accountFilter, filterRole);

		if (!errors.hasFieldErrors("confirmPassword") && adminResetPasswordDTO.getNewPassword() != null
				&& !adminResetPasswordDTO.getNewPassword().equals(adminResetPasswordDTO.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "confirmPassword.mismatch",
					"Xác nhận mật khẩu không khớp");
		}

		if (errors.hasErrors()) {
			model.addAttribute("openModal", "resetPasswordModal");
			populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
			return INDEX_VIEW;
		}

		try {
			accountManagementService.resetPassword(adminResetPasswordDTO);
			redirectAttributes.addFlashAttribute("successMessage",
					"Đặt lại mật khẩu thành công và đã gửi email thông tin đăng nhập");
			appendFilterAttributes(redirectAttributes, accountFilter);
			return "redirect:/admin/accounts";
		} catch (IllegalArgumentException | IllegalStateException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			model.addAttribute("openModal", "resetPasswordModal");
			populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
			return INDEX_VIEW;
		}
	}

	@PostMapping("/update-role")
	public String updateRole(@Valid @ModelAttribute("updateAccountRoleDTO") UpdateAccountRoleDTO updateAccountRoleDTO,
			BindingResult errors, @ModelAttribute("accountFilter") AccountFilterDTO accountFilter,
			@RequestParam(value = "filterRole", required = false) String filterRole, ModelMap model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		String redirect = validateAdminAccess(session);
		if (redirect != null) {
			return redirect;
		}

		mergeFilterRole(accountFilter, filterRole);

		if (errors.hasErrors()) {
			model.addAttribute("openModal", "updateRoleModal");
			populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
			return INDEX_VIEW;
		}

		try {
			accountManagementService.updateRole(updateAccountRoleDTO, (String) session.getAttribute("LOGIN_USER"));
			redirectAttributes.addFlashAttribute("successMessage", "Cập nhật role thành công");
			appendFilterAttributes(redirectAttributes, accountFilter);
			return "redirect:/admin/accounts";
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			model.addAttribute("openModal", "updateRoleModal");
			populatePageModel(model, accountFilter, (String) session.getAttribute("LOGIN_USER"));
			return INDEX_VIEW;
		}
	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute("accountFilter") AccountFilterDTO accountFilter,
			@RequestParam(value = "filterRole", required = false) String filterRole,
			@RequestParam("deleteUsername") String deleteUsername, HttpSession session,
			RedirectAttributes redirectAttributes) {
		String redirect = validateAdminAccess(session);
		if (redirect != null) {
			return redirect;
		}

		mergeFilterRole(accountFilter, filterRole);

		try {
			accountManagementService.deleteAccount(deleteUsername, (String) session.getAttribute("LOGIN_USER"));
			redirectAttributes.addFlashAttribute("successMessage", "Xóa tài khoản thành công");
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
		}

		appendFilterAttributes(redirectAttributes, accountFilter);
		return "redirect:/admin/accounts";
	}

	private void populatePageModel(ModelMap model, AccountFilterDTO accountFilter, String currentUsername) {
		model.addAttribute("pageTitle", "Quản lý tài khoản");
		model.addAttribute("roleOptions", Arrays.asList("PGV", "GIAOVIEN", "SINHVIEN"));

		List<AccountManagementItemDTO> items = new ArrayList<>();
		for (Account account : accountManagementService.getAccounts(accountFilter)) {
			items.add(accountManagementService.buildAccountItem(account, currentUsername));
		}

		model.addAttribute("accounts", items);
		model.addAttribute("availableLecturers", accountManagementService.getAvailableLecturers());

		if (!model.containsAttribute("createPgvAccountDTO")) {
			model.addAttribute("createPgvAccountDTO", new CreatePgvAccountDTO());
		}

		if (!model.containsAttribute("createLecturerAccountDTO")) {
			model.addAttribute("createLecturerAccountDTO", new CreateLecturerAccountDTO());
		}

		if (!model.containsAttribute("adminResetPasswordDTO")) {
			model.addAttribute("adminResetPasswordDTO", new AdminResetPasswordDTO());
		}

		if (!model.containsAttribute("updateAccountRoleDTO")) {
			model.addAttribute("updateAccountRoleDTO", new UpdateAccountRoleDTO());
		}
	}

	private void appendFilterAttributes(RedirectAttributes redirectAttributes, AccountFilterDTO accountFilter) {
		if (accountFilter == null) {
			return;
		}

		if (hasText(accountFilter.getRole())) {
			redirectAttributes.addAttribute("role", accountFilter.getRole().trim());
		}

		if (hasText(accountFilter.getKeyword())) {
			redirectAttributes.addAttribute("keyword", accountFilter.getKeyword().trim());
		}
	}

	private void mergeFilterRole(AccountFilterDTO accountFilter, String filterRole) {
		if (accountFilter == null) {
			return;
		}

		if (hasText(filterRole)) {
			accountFilter.setRole(filterRole.trim());
		}
	}

	private String validateAdminAccess(HttpSession session) {
		String role = (String) session.getAttribute("ROLE");
		if ("PGV".equals(role)) {
			return null;
		}

		if ("GIAOVIEN".equals(role)) {
			return "redirect:/lecturers/home";
		}

		if ("SINHVIEN".equals(role)) {
			return "redirect:/students/home";
		}

		return "redirect:/hello";
	}

	private boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}
}
