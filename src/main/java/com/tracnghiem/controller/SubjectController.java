package com.tracnghiem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
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

import com.tracnghiem.dto.SubjectDTO;
import com.tracnghiem.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

	private static final String INDEX_VIEW = "Subject/Index";
	private static final String REDIRECT_INDEX = "redirect:/subjects";
	private static final String SUBJECT_MODEL_ATTRIBUTE = "subjectDTO";

	@Autowired
	private SubjectService subjectService;

	private void loadPageData(ModelMap model, int page, String search) {
		int pageSize = 10;

		if (page < 1) {
			page = 1;
		}

		model.addAttribute("subjects", subjectService.getSubjects(page, pageSize, search));
		model.addAttribute(SUBJECT_MODEL_ATTRIBUTE, new SubjectDTO());
		model.addAttribute("search", search);

		long total = subjectService.countSubjects(search);
		int totalPages = (int) Math.ceil((double) total / pageSize);

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
	}

	@GetMapping
	public String index(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String search,
			ModelMap model) {
		loadPageData(model, page, search);
		return INDEX_VIEW;
	}

	@PostMapping("/add")
	public String add(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String search,
			@Validated @ModelAttribute(SUBJECT_MODEL_ATTRIBUTE) SubjectDTO subjectDTO, BindingResult errors,
			ModelMap model, RedirectAttributes redirectAttributes) {

		if (errors.hasErrors()) {
			loadPageData(model, page, search);
			return INDEX_VIEW;
		}

		try {
			subjectService.addSubject(subjectDTO);
			redirectAttributes.addFlashAttribute("successMessage", "Thêm môn học thành công.");
			return buildRedirectUrl(page, search);
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			loadPageData(model, page, search);
			return INDEX_VIEW;
		}
	}

	@PostMapping("/update")
	public String update(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String search,
			@Validated @ModelAttribute(SUBJECT_MODEL_ATTRIBUTE) SubjectDTO subjectDTO, BindingResult errors,
			ModelMap model, RedirectAttributes redirectAttributes) {

		if (errors.hasErrors()) {
			loadPageData(model, page, search);
			return INDEX_VIEW;
		}

		try {
			subjectService.updateSubject(subjectDTO);
			redirectAttributes.addFlashAttribute("successMessage", "Cập nhật môn học thành công.");
			return buildRedirectUrl(page, search);
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			loadPageData(model, page, search);
			return INDEX_VIEW;
		}
	}

	@PostMapping("/delete")
	public String delete(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String search,
			@ModelAttribute(SUBJECT_MODEL_ATTRIBUTE) SubjectDTO subjectDTO, ModelMap model,
			RedirectAttributes redirectAttributes) {

		try {
			subjectService.deleteSubject(subjectDTO);
			redirectAttributes.addFlashAttribute("successMessage", "Xóa môn học thành công.");
			return buildRedirectUrl(page, search);
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			loadPageData(model, page, search);
			return INDEX_VIEW;
		}
	}

	private String buildRedirectUrl(int page, String search) {
		String url = REDIRECT_INDEX + "?page=" + page;
		if (search != null && !search.trim().isEmpty()) {
			String encodedSearch = URLEncoder.encode(search.trim(), StandardCharsets.UTF_8);
			url += "&search=" + encodedSearch;
		}
		return url;
	}

	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"danh-sach-mon-hoc.xlsx\"");

		List<com.tracnghiem.entity.Subject> subjects = subjectService.getAllSubjects();

		try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {

			Sheet sheet = workbook.createSheet("Môn học");

			// Header
			Row header = sheet.createRow(0);
			Cell cell0 = header.createCell(0);
			cell0.setCellValue("Mã Môn học");
			Cell cell1 = header.createCell(1);
			cell1.setCellValue("Tên Môn học");

			CellStyle headerStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			headerStyle.setFont(font);
			cell0.setCellStyle(headerStyle);
			cell1.setCellStyle(headerStyle);

			int rowIdx = 1;
			for (com.tracnghiem.entity.Subject subject : subjects) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(subject.getSubjectId());
				row.createCell(1).setCellValue(subject.getSubjectName());
			}

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);

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
			List<SubjectDTO> dtos = new ArrayList<>();

			// Skip header (row 0), read from row 1
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}

				Cell idCell = row.getCell(0);
				Cell nameCell = row.getCell(1);

				if (idCell == null && nameCell == null) {
					continue;
				}

				String subjectId = getCellValueAsString(idCell);
				String subjectName = getCellValueAsString(nameCell);

				if (subjectId.trim().isEmpty() && subjectName.trim().isEmpty()) {
					continue;
				}

				SubjectDTO dto = new SubjectDTO();
				dto.setSubjectId(subjectId.trim());
				dto.setSubjectName(subjectName.trim());
				dtos.add(dto);
			}

			if (dtos.isEmpty()) {
				redirectAttributes.addFlashAttribute("errorMessage",
						"Không tìm thấy dữ liệu môn học hợp lệ trong tệp Excel.");
			} else {
				subjectService.importSubjects(dtos);
				redirectAttributes.addFlashAttribute("successMessage",
						"Nhập danh sách môn học từ Excel thành công (Đã nhập " + dtos.size() + " môn học).");
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
