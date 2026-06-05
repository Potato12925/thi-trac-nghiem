package com.tracnghiem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import com.tracnghiem.dto.QuestionDTO;
import com.tracnghiem.entity.Question;
import com.tracnghiem.service.QuestionService;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	private static final String INDEX_VIEW = "Question/Index";
	private static final String REDIRECT_INDEX = "redirect:/questions";

	@Autowired
	private QuestionService questionService;

	@GetMapping
	public String showQuestionPage(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword, ModelMap model, HttpSession session) {
		String role = (String) session.getAttribute("ROLE");
		String loggedUser = (String) session.getAttribute("LOGIN_USER");
		boolean isLecturer = "GIAOVIEN".equals(role);

		prepareQuestionPage(model, page, keyword, role, loggedUser);

		QuestionDTO questionDTO = new QuestionDTO();
		if (isLecturer) {
			questionDTO.setLecturerId(loggedUser);
		}
		model.addAttribute("questionDTO", questionDTO);
		model.addAttribute("keyword", keyword);
		model.addAttribute("isLecturer", isLecturer);
		model.addAttribute("loggedLecturerId", loggedUser);

		return INDEX_VIEW;
	}

	@PostMapping("/add")
	public String createQuestion(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@Validated @ModelAttribute("questionDTO") QuestionDTO questionForm, BindingResult validationResult,
			ModelMap model, HttpSession session) {

		if (validationResult.hasErrors()) {
			return renderQuestionPage(model, page, keyword, session);
		}

		try {
			String role = (String) session.getAttribute("ROLE");
			String loggedUser = (String) session.getAttribute("LOGIN_USER");
			String realPath = session.getServletContext().getRealPath("/uploads/questions");
			questionService.addQuestion(questionForm, role, loggedUser, realPath);
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (IllegalArgumentException exception) {
			model.addAttribute("errorMessage", exception.getMessage());
			return renderQuestionPage(model, page, keyword, session);
		}
	}

	@PostMapping("/update")
	public String editQuestion(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@Validated @ModelAttribute("questionDTO") QuestionDTO questionForm, BindingResult validationResult,
			ModelMap model, HttpSession session) {

		if (validationResult.hasErrors()) {
			return renderQuestionPage(model, page, keyword, session);
		}

		try {
			String role = (String) session.getAttribute("ROLE");
			String loggedUser = (String) session.getAttribute("LOGIN_USER");
			String realPath = session.getServletContext().getRealPath("/uploads/questions");
			questionService.updateQuestion(questionForm, role, loggedUser, realPath);
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (IllegalArgumentException exception) {
			model.addAttribute("errorMessage", exception.getMessage());
			return renderQuestionPage(model, page, keyword, session);
		}
	}

	@GetMapping(value = "/check-duplicate", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<Map<String, Object>> checkDuplicate(@RequestParam("content") String content,
			@RequestParam("subjectId") String subjectId,
			@RequestParam(value = "excludeId", required = false) Integer excludeId) {

		List<Question> duplicates = questionService.findPotentialDuplicates(content, subjectId, excludeId);
		List<Map<String, Object>> result = new ArrayList<>();
		for (Question q : duplicates) {
			Map<String, Object> map = new HashMap<>();
			map.put("questionId", q.getQuestionId());
			map.put("content", q.getContent());
			map.put("level", q.getLevel());
			map.put("subjectId", q.getSubject() != null ? q.getSubject().getSubjectId() : "");
			map.put("similarity", Math.round(questionService.calculateSimilarity(content, q.getContent()) * 100));
			result.add(map);
		}
		return result;
	}

	@PostMapping(value = "/upload-image", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file, HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		if (file.isEmpty()) {
			response.put("success", false);
			response.put("message", "File is empty");
			return response;
		}
		try {
			String realPath = session.getServletContext().getRealPath("/uploads/questions");
			String imageUrl = questionService.saveUploadedImage(file, realPath);
			if (imageUrl != null) {
				response.put("success", true);
				response.put("imageUrl", imageUrl);
			} else {
				response.put("success", false);
				response.put("message", "Failed to save file");
			}
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e.getMessage());
		}
		return response;
	}

	@PostMapping("/delete")
	public String removeQuestion(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@Validated @ModelAttribute("questionDTO") QuestionDTO questionForm, BindingResult validationResult,
			ModelMap model, HttpSession session) {

		if (validationResult.hasErrors()) {
			return renderQuestionPage(model, page, keyword, session);
		}

		try {
			String role = (String) session.getAttribute("ROLE");
			String loggedUser = (String) session.getAttribute("LOGIN_USER");
			questionService.deleteQuestion(questionForm, role, loggedUser);
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (IllegalArgumentException exception) {
			model.addAttribute("errorMessage", exception.getMessage());
			return renderQuestionPage(model, page, keyword, session);
		}
	}

	@PostMapping("/save")
	public String save(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword,
			@RequestParam("actionsData") String actionsData, ModelMap model, RedirectAttributes redirectAttributes,
			HttpSession session) {

		if (actionsData == null || actionsData.trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không có thay đổi nào để ghi.");
			return REDIRECT_INDEX + buildQuery(page, keyword);
		}

		try {
			String role = (String) session.getAttribute("ROLE");
			String loggedUser = (String) session.getAttribute("LOGIN_USER");
			questionService.savePendingActions(actionsData, role, loggedUser);
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

	private void prepareQuestionPage(ModelMap model, int page, String keyword, String role, String loggedUser) {
		int pageSize = 10;

		List<Question> questions = questionService.getQuestions(page, pageSize, keyword, role, loggedUser);

		long totalQuestions = questionService.countQuestion(keyword, role, loggedUser);

		int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

		model.addAttribute("questions", questions);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
	}

	private String renderQuestionPage(ModelMap model, int page, String keyword, HttpSession session) {
		String role = (String) session.getAttribute("ROLE");
		String loggedUser = (String) session.getAttribute("LOGIN_USER");
		prepareQuestionPage(model, page, keyword, role, loggedUser);
		boolean isLecturer = "GIAOVIEN".equals(role);
		model.addAttribute("keyword", keyword);
		model.addAttribute("isLecturer", isLecturer);
		model.addAttribute("loggedLecturerId", loggedUser);
		return INDEX_VIEW;
	}

	private String buildQuery(int page, String keyword) {
		StringBuilder query = new StringBuilder("?page=").append(page);
		if (keyword != null && !keyword.trim().isEmpty()) {
			query.append("&keyword=").append(URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8));
		}
		return query.toString();
	}

	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"danh-sach-bo-de.xlsx\"");

		String role = (String) session.getAttribute("ROLE");
		String loggedUser = (String) session.getAttribute("LOGIN_USER");

		List<Question> questions = questionService.getAllQuestions(role, loggedUser);

		try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
			Sheet sheet = workbook.createSheet("Bộ đề");

			// Header
			Row header = sheet.createRow(0);
			String[] headers = { "Mã câu hỏi", "Mã môn học", "Trình độ", "Nội dung", "Đáp án A", "Đáp án B", "Đáp án C",
					"Đáp án D", "Đáp án đúng", "Mã GV" };
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
			for (Question question : questions) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0)
						.setCellValue(question.getQuestionId() != null ? String.valueOf(question.getQuestionId()) : "");
				row.createCell(1)
						.setCellValue(question.getSubject() != null ? question.getSubject().getSubjectId() : "");
				row.createCell(2).setCellValue(question.getLevel());
				row.createCell(3).setCellValue(question.getContent());
				row.createCell(4).setCellValue(question.getOptionA());
				row.createCell(5).setCellValue(question.getOptionB());
				row.createCell(6).setCellValue(question.getOptionC());
				row.createCell(7).setCellValue(question.getOptionD());
				row.createCell(8).setCellValue(question.getCorrectAnswer());
				row.createCell(9)
						.setCellValue(question.getLecturer() != null ? question.getLecturer().getLecturerId() : "");
			}

			for (int j = 0; j < headers.length; j++) {
				sheet.autoSizeColumn(j);
			}

			workbook.write(out);
		}
	}

	@PostMapping("/import")
	public String importFromExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			HttpSession session) {
		String role = (String) session.getAttribute("ROLE");
		String loggedUser = (String) session.getAttribute("LOGIN_USER");

		if ("GIAOVIEN".equals(role)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Giáo viên không có quyền nhập câu hỏi.");
			return REDIRECT_INDEX;
		}

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn một tệp Excel để nhập.");
			return REDIRECT_INDEX;
		}

		try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
			Sheet sheet = workbook.getSheetAt(0);
			List<QuestionDTO> dtos = new ArrayList<>();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}

				Cell subIdCell = row.getCell(1); // Mã môn
				Cell lvlCell = row.getCell(2); // Trình độ
				Cell cntCell = row.getCell(3); // Nội dung
				Cell optACell = row.getCell(4); // A
				Cell optBCell = row.getCell(5); // B
				Cell optCCell = row.getCell(6); // C
				Cell optDCell = row.getCell(7); // D
				Cell ansCell = row.getCell(8); // Đáp án đúng
				Cell lecCell = row.getCell(9); // Mã GV

				if (subIdCell == null && cntCell == null) {
					continue;
				}

				String subjectId = getCellValueAsString(subIdCell).trim();
				String level = getCellValueAsString(lvlCell).trim();
				String content = getCellValueAsString(cntCell).trim();
				String optionA = getCellValueAsString(optACell).trim();
				String optionB = getCellValueAsString(optBCell).trim();
				String optionC = getCellValueAsString(optCCell).trim();
				String optionD = getCellValueAsString(optDCell).trim();
				String correctAnswer = getCellValueAsString(ansCell).trim();
				String lecturerId = getCellValueAsString(lecCell).trim();

				if (subjectId.isEmpty() && content.isEmpty()) {
					continue;
				}

				QuestionDTO dto = new QuestionDTO();
				dto.setSubjectId(subjectId);
				dto.setLevel(level);
				dto.setContent(content);
				dto.setOptionA(optionA);
				dto.setOptionB(optionB);
				dto.setOptionC(optionC);
				dto.setOptionD(optionD);
				dto.setCorrectAnswer(correctAnswer);
				dto.setLecturerId(lecturerId);
				dtos.add(dto);
			}

			if (dtos.isEmpty()) {
				redirectAttributes.addFlashAttribute("errorMessage",
						"Không tìm thấy dữ liệu câu hỏi hợp lệ trong tệp Excel.");
			} else {
				questionService.importQuestions(dtos, role, loggedUser);
				redirectAttributes.addFlashAttribute("successMessage",
						"Nhập danh sách câu hỏi từ Excel thành công (Đã nhập " + dtos.size() + " câu hỏi).");
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
