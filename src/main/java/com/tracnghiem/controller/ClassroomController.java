package com.tracnghiem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.tracnghiem.dto.ClassroomDTO;
import com.tracnghiem.dto.StudentScoreDTO;
import com.tracnghiem.entity.Classroom;
import com.tracnghiem.service.ClassroomService;
import com.tracnghiem.service.ScoreService;
import com.tracnghiem.service.SubjectService;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

	private static final String INDEX_VIEW = "Classroom/Index";
	private static final String REDIRECT_INDEX = "redirect:/classrooms";

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private SubjectService subjectService;

	@GetMapping
	public String showClassroomPage(@RequestParam(defaultValue = "1") int page, ModelMap model) {
		prepareClassroomPage(model, page);

		model.addAttribute("classroomDTO", new ClassroomDTO());

		return INDEX_VIEW;
	}

	@GetMapping(value = "/{classId}/analytics", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getClassroomAnalytics(@PathVariable("classId") String classId,
			@RequestParam(value = "subjectId", required = false) String subjectId,
			@RequestParam(value = "tryNumber", defaultValue = "1") Short tryNumber) {

		Map<String, Object> response = new HashMap<>();
		try {
			response.put("subjects", subjectService.getAllSubjects().stream().map(sub -> {
				Map<String, String> subm = new HashMap<>();
				subm.put("subjectId", sub.getSubjectId().trim());
				subm.put("subjectName", sub.getSubjectName().trim());
				return subm;
			}).collect(Collectors.toList()));

			if (subjectId == null || subjectId.trim().isEmpty()) {
				return response;
			}

			List<StudentScoreDTO> scores = scoreService.getClassScores(classId, subjectId, tryNumber);

			// Statistics calculation
			int totalStudents = scores.size();
			int attemptedCount = 0;
			float sum = 0.0f;
			Float highest = null;
			Float lowest = null;

			// Grade distribution map
			Map<String, Integer> distribution = new HashMap<>();
			String[] grades = { "A+", "A", "B+", "B", "C+", "C", "D+", "D", "F" };
			for (String g : grades) {
				distribution.put(g, 0);
			}

			List<StudentScoreDTO> takenExams = new ArrayList<>();

			for (StudentScoreDTO s : scores) {
				Float score = s.getScore();
				if (score != null) {
					attemptedCount++;
					sum += score;
					if (highest == null || score > highest) {
						highest = score;
					}
					if (lowest == null || score < lowest) {
						lowest = score;
					}

					String letterGrade = s.getLetterGrade();
					distribution.put(letterGrade, distribution.getOrDefault(letterGrade, 0) + 1);
					takenExams.add(s);
				} else {
					distribution.put("F", distribution.getOrDefault("F", 0) + 1);
				}
			}

			// Rank top students (taken exams sorted by score descending)
			takenExams.sort((s1, s2) -> s2.getScore().compareTo(s1.getScore()));
			List<Map<String, Object>> topStudents = new ArrayList<>();
			int limit = Math.min(5, takenExams.size());
			for (int i = 0; i < limit; i++) {
				StudentScoreDTO s = takenExams.get(i);
				Map<String, Object> sm = new HashMap<>();
				sm.put("studentId", s.getStudentId().trim());
				sm.put("fullName", (s.getLastName() + " " + s.getFirstName()).trim());
				sm.put("score", s.getScore());
				sm.put("grade", s.getLetterGrade());
				topStudents.add(sm);
			}

			int passCount = 0;
			int failCount = 0;
			for (StudentScoreDTO s : takenExams) {
				if (s.getScore() >= 4.0f) {
					passCount++;
				} else {
					failCount++;
				}
			}

			response.put("totalStudents", totalStudents);
			response.put("attemptedCount", attemptedCount);
			response.put("notAttemptedCount", totalStudents - attemptedCount);
			response.put("averageScore",
					attemptedCount > 0 ? (double) Math.round(sum / attemptedCount * 100) / 100 : 0.0);
			response.put("highestScore", highest != null ? highest : 0.0);
			response.put("lowestScore", lowest != null ? lowest : 0.0);
			response.put("passCount", passCount);
			response.put("failCount", failCount + (totalStudents - attemptedCount)); // Count not attempted as failed
			response.put("distribution", distribution);
			response.put("topStudents", topStudents);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", e.getMessage());
		}
		return response;
	}

	@PostMapping("/add")
	public String add(@RequestParam(defaultValue = "1") int page,
			@Validated @ModelAttribute("classroomDTO") ClassroomDTO classroomDTO, BindingResult validationResult,
			ModelMap model) {
		if (validationResult.hasErrors()) {
			return renderClassroomPage(model, page);
		}

		try {
			classroomService.addClassroom(classroomDTO);

			return REDIRECT_INDEX;

		} catch (IllegalArgumentException exception) {

			model.addAttribute("errorMessage", exception.getMessage());

			return renderClassroomPage(model, page);
		}
	}

	@PutMapping("/update")
	public String update(@RequestParam(defaultValue = "1") int page,
			@Validated @ModelAttribute("classroomDTO") ClassroomDTO classroomDTO, BindingResult validationResult,
			ModelMap model) {

		if (validationResult.hasErrors()) {
			model.addAttribute("classrooms", classroomService.getAllClassrooms());

			return INDEX_VIEW;

		}

		try {
			classroomService.updateClassroom(classroomDTO);

			return REDIRECT_INDEX;

		} catch (IllegalArgumentException exception) {

			model.addAttribute("errorMessage", exception.getMessage());

			return renderClassroomPage(model, page);
		}
	}

	@DeleteMapping("/delete")
	public String delete(@RequestParam(defaultValue = "1") int page,
			@Validated @ModelAttribute("classroomDTO") ClassroomDTO classroomDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("classrooms", classroomService.getAllClassrooms());

			return INDEX_VIEW;
		}

		try {
			classroomService.deleteClassroom(classroomDTO);
			return REDIRECT_INDEX;

		} catch (IllegalArgumentException exception) {

			model.addAttribute("errorMessage", exception.getMessage());

			return renderClassroomPage(model, page);
		}
	}

	@PostMapping("/save")
	public String save(@RequestParam(defaultValue = "1") int page, @RequestParam("actionsData") String actionsData,
			ModelMap model, RedirectAttributes redirectAttributes) {

		if (actionsData == null || actionsData.trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không có thay đổi nào để ghi.");
			return REDIRECT_INDEX + "?page=" + page;
		}

		try {
			classroomService.savePendingActions(actionsData);
			redirectAttributes.addFlashAttribute("successMessage", "Ghi các thay đổi xuống CSDL thành công.");
			return REDIRECT_INDEX + "?page=" + page;
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ghi dữ liệu: " + ex.getMessage());
			return REDIRECT_INDEX + "?page=" + page;
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống: " + ex.getMessage());
			return REDIRECT_INDEX + "?page=" + page;
		}
	}

	private void prepareClassroomPage(ModelMap model, int page) {
		int pageSize = 10;

		List<Classroom> classrooms = classroomService.getClassrooms(page, pageSize);

		long totalQuestions = classroomService.countClassrooms();

		int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

		model.addAttribute("classrooms", classrooms);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
	}

	private String renderClassroomPage(ModelMap model, int page) {

		prepareClassroomPage(model, page);

		return INDEX_VIEW;
	}

	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"danh-sach-lop-hoc.xlsx\"");

		List<Classroom> classrooms = classroomService.getAllClassrooms();

		try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
			Sheet sheet = workbook.createSheet("Lớp học");

			// Header
			Row header = sheet.createRow(0);
			Cell cell0 = header.createCell(0);
			cell0.setCellValue("Mã lớp");
			Cell cell1 = header.createCell(1);
			cell1.setCellValue("Tên lớp");

			CellStyle headerStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			headerStyle.setFont(font);
			cell0.setCellStyle(headerStyle);
			cell1.setCellStyle(headerStyle);

			int rowIdx = 1;
			for (Classroom cr : classrooms) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(cr.getClassId());
				row.createCell(1).setCellValue(cr.getClassName());
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
			List<ClassroomDTO> dtos = new ArrayList<>();

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

				String classId = getCellValueAsString(idCell);
				String className = getCellValueAsString(nameCell);

				if (classId.trim().isEmpty() && className.trim().isEmpty()) {
					continue;
				}

				ClassroomDTO dto = new ClassroomDTO();
				dto.setClassId(classId.trim());
				dto.setClassName(className.trim());
				dtos.add(dto);
			}

			if (dtos.isEmpty()) {
				redirectAttributes.addFlashAttribute("errorMessage",
						"Không tìm thấy dữ liệu lớp học hợp lệ trong tệp Excel.");
			} else {
				classroomService.importClassrooms(dtos);
				redirectAttributes.addFlashAttribute("successMessage",
						"Nhập danh sách lớp từ Excel thành công (Đã nhập " + dtos.size() + " lớp).");
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
