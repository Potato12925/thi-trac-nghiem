package com.tracnghiem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;
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

	private void prepareLecturerPage(ModelMap model, int page, String keyword) {
		int pageSize = 10;

		if (keyword != null && !keyword.trim().isEmpty()) {
			model.addAttribute("lecturers", lecturerService.getLecturers(page, pageSize, keyword));
			model.addAttribute("keyword", keyword);
			long total = lecturerService.countLecturers(keyword);
			int totalPages = (int) Math.ceil((double) total / pageSize);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
		} else {
			model.addAttribute("lecturers", lecturerService.getLecturers(page, pageSize));
			long total = lecturerService.countLecturers();
			int totalPages = (int) Math.ceil((double) total / pageSize);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
		}
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
	public String sendEmailOtp(@Validated @ModelAttribute("changeEmailDTO") ChangeEmailDTO changeEmailDTO,
			BindingResult errors, ModelMap model, HttpSession session) {

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
			BindingResult errors, ModelMap model, HttpSession session, RedirectAttributes redirectAttributes) {

		String redirect = validateLecturerAccess(session);
		if (redirect != null) {
			return redirect;
		}

		if (errors.hasErrors()) {
			populateSettingsPageModel(model, session, confirmEmailChangeDTO.getNewEmail(),
					confirmEmailChangeDTO.getNewEmail());
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
			populateSettingsPageModel(model, session, confirmEmailChangeDTO.getNewEmail(),
					confirmEmailChangeDTO.getNewEmail());
			model.addAttribute("errorMessage", ex.getMessage());
			return "Lecturer/Settings";
		}
	}

	@GetMapping
	public String index(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword,
			ModelMap model) {
		prepareLecturerPage(model, page, keyword);
		model.addAttribute("lecturerDTO", new LecturerDTO());
		return INDEX_VIEW;
	}

	@PostMapping("/add")
	public String add(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword,
			@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors, ModelMap model) {

		if (errors.hasErrors()) {
			prepareLecturerPage(model, page, keyword);
			model.addAttribute("lecturerDTO", lecturerDTO);
			return INDEX_VIEW;
		}

		try {
			lecturerService.addLecturer(lecturerDTO);
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			prepareLecturerPage(model, page, keyword);
			model.addAttribute("lecturerDTO", lecturerDTO);
			return INDEX_VIEW;
		}
	}

	@PostMapping("/update")
	public String update(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword,
			@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors, ModelMap model) {
		if (errors.hasErrors()) {
			prepareLecturerPage(model, page, keyword);
			model.addAttribute("lecturerDTO", lecturerDTO);
			return INDEX_VIEW;
		}

		try {
			lecturerService.updateLecturer(lecturerDTO);
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			prepareLecturerPage(model, page, keyword);
			model.addAttribute("lecturerDTO", lecturerDTO);
			return INDEX_VIEW;
		}
	}

	@PostMapping("/delete")
	public String delete(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword,
			@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors, ModelMap model) {
		if (errors.hasErrors()) {
			prepareLecturerPage(model, page, keyword);
			model.addAttribute("lecturerDTO", lecturerDTO);
			return INDEX_VIEW;
		}

		try {
			lecturerService.deleteLecturer(lecturerDTO);
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			prepareLecturerPage(model, page, keyword);
			model.addAttribute("lecturerDTO", lecturerDTO);
			return INDEX_VIEW;
		}
	}

	@PostMapping("/save")
	public String save(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword,
			@RequestParam("actionsData") String actionsData, ModelMap model, RedirectAttributes redirectAttributes) {

		if (actionsData == null || actionsData.trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không có thay đổi nào để ghi.");
			return REDIRECT_INDEX + buildQuery(page, keyword);
		}

		try {
			lecturerService.savePendingActions(actionsData);
			redirectAttributes.addFlashAttribute("successMessage", "Ghi các thay đổi xuống CSDL thành công.");
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ghi dữ liệu: " + ex.getMessage());
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống: " + ex.getMessage());
			return REDIRECT_INDEX + buildQuery(page, keyword);
		}
	}

	private String buildQuery(int page, String keyword) {
		StringBuilder query = new StringBuilder("?page=").append(page);
		if (keyword != null && !keyword.trim().isEmpty()) {
			query.append("&keyword=").append(URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8));
		}
		return query.toString();
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

	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"danh-sach-giang-vien.xlsx\"");

		List<Lecturer> lecturers = lecturerService.getAllLecturers();

		try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
			Sheet sheet = workbook.createSheet("Giảng viên");

			// Header
			Row header = sheet.createRow(0);
			String[] headers = { "Mã giảng viên", "Họ", "Tên", "Số điện thoại", "Địa chỉ" };
			for (int j = 0; j < headers.length; j++) {
				Cell cell = header.createCell(j);
				cell.setCellValue(headers[j]);
				CellStyle headerStyle = workbook.createCellStyle();
				Font font = workbook.createFont();
				font.setBold(true);
				headerStyle.setFont(font);
				cell.setCellStyle(headerStyle);
			}

			int rowIdx = 1;
			for (Lecturer lecturer : lecturers) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(lecturer.getLecturerId());
				row.createCell(1).setCellValue(lecturer.getLastName());
				row.createCell(2).setCellValue(lecturer.getFirstName());
				row.createCell(3).setCellValue(lecturer.getPhoneNumber());
				row.createCell(4).setCellValue(lecturer.getAddress());
			}

			for (int j = 0; j < headers.length; j++) {
				sheet.autoSizeColumn(j);
			}

			workbook.write(out);
		}
	}

	@PostMapping("/import")
	public String importFromExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn một tệp Excel để nhập.");
			return REDIRECT_INDEX;
		}

		try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
			Sheet sheet = workbook.getSheetAt(0);
			List<LecturerDTO> dtos = new ArrayList<>();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}

				Cell idCell = row.getCell(0);
				Cell lastCell = row.getCell(1);
				Cell firstCell = row.getCell(2);
				Cell phoneCell = row.getCell(3);
				Cell addrCell = row.getCell(4);

				if (idCell == null && lastCell == null && firstCell == null) {
					continue;
				}

				String lecturerId = getCellValueAsString(idCell).trim();
				String lastName = getCellValueAsString(lastCell).trim();
				String firstName = getCellValueAsString(firstCell).trim();
				String phoneNumber = getCellValueAsString(phoneCell).trim();
				String address = getCellValueAsString(addrCell).trim();

				if (lecturerId.isEmpty() && lastName.isEmpty() && firstName.isEmpty()) {
					continue;
				}

				LecturerDTO dto = new LecturerDTO();
				dto.setLecturerId(lecturerId);
				dto.setLastName(lastName);
				dto.setFirstName(firstName);
				dto.setPhoneNumber(phoneNumber);
				dto.setAddress(address);
				dtos.add(dto);
			}

			if (dtos.isEmpty()) {
				redirectAttributes.addFlashAttribute("errorMessage",
						"Không tìm thấy dữ liệu giảng viên hợp lệ trong tệp Excel.");
			} else {
				lecturerService.importLecturers(dtos);
				redirectAttributes.addFlashAttribute("successMessage",
						"Nhập danh sách giảng viên từ Excel thành công (Đã nhập " + dtos.size() + " giảng viên).");
			}

		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi xử lý tệp Excel: " + e.getMessage());
		}

		return REDIRECT_INDEX;
	}

	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			}
			double numericVal = cell.getNumericCellValue();
			if (numericVal == (long) numericVal) {
				return String.valueOf((long) numericVal);
			}
			return String.valueOf(numericVal);
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			try {
				return cell.getStringCellValue();
			} catch (Exception e) {
				return String.valueOf(cell.getNumericCellValue());
			}
		default:
			return "";
		}
	}
}
