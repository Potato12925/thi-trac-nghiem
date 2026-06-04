package com.tracnghiem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.entity.Student;
import com.tracnghiem.service.ClassroomService;
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
        String studentId = (String) session.getAttribute("LOGIN_USER");
        Student student = studentId != null ? studentService.getStudentById(studentId) : null;

        model.addAttribute("pageTitle", "Student Home");
        model.addAttribute("studentProfile", student);
        model.addAttribute("today", new Date());

        return "Student/Home";
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

	@PostMapping("/save")
	public String save(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, name = "filterClassId") String filterClassId,
			@RequestParam("actionsData") String actionsData, ModelMap model, RedirectAttributes redirectAttributes) {

		if (actionsData == null || actionsData.trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không có thay đổi nào để ghi.");
			return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
		}

		try {
			studentService.savePendingActions(actionsData);
			redirectAttributes.addFlashAttribute("successMessage", "Ghi các thay đổi xuống CSDL thành công.");
			return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ghi dữ liệu: " + ex.getMessage());
			return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống: " + ex.getMessage());
			return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
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

	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"danh-sach-sinh-vien.xlsx\"");

		List<Student> students = studentService.getAllStudents();

		try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
			Sheet sheet = workbook.createSheet("Sinh viên");

			// Header
			Row header = sheet.createRow(0);
			String[] headers = {"Mã sinh viên", "Họ", "Tên", "Ngày sinh", "Địa chỉ", "Mã lớp"};
			for (int j = 0; j < headers.length; j++) {
				Cell cell = header.createCell(j);
				cell.setCellValue(headers[j]);
				CellStyle headerStyle = workbook.createCellStyle();
				Font font = workbook.createFont();
				font.setBold(true);
				headerStyle.setFont(font);
				cell.setCellStyle(headerStyle);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int rowIdx = 1;
			for (Student student : students) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(student.getStudentId());
				row.createCell(1).setCellValue(student.getLastName());
				row.createCell(2).setCellValue(student.getFirstName());
				
				String dateStr = student.getBirthDate() != null ? sdf.format(student.getBirthDate()) : "";
				row.createCell(3).setCellValue(dateStr);
				
				row.createCell(4).setCellValue(student.getAddress());
				row.createCell(5).setCellValue(student.getClassRoom() != null ? student.getClassRoom().getClassId() : "");
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
			List<StudentDTO> dtos = new ArrayList<>();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}

				Cell idCell = row.getCell(0);
				Cell lastCell = row.getCell(1);
				Cell firstCell = row.getCell(2);
				Cell birthCell = row.getCell(3);
				Cell addrCell = row.getCell(4);
				Cell classCell = row.getCell(5);

				if (idCell == null && lastCell == null && firstCell == null) {
					continue;
				}

				String studentId = getCellValueAsString(idCell).trim();
				String lastName = getCellValueAsString(lastCell).trim();
				String firstName = getCellValueAsString(firstCell).trim();
				String address = getCellValueAsString(addrCell).trim();
				String classId = getCellValueAsString(classCell).trim();

				if (studentId.isEmpty() && lastName.isEmpty() && firstName.isEmpty()) {
					continue;
				}

				Date birthDate = null;
				if (birthCell != null) {
					if (birthCell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC && DateUtil.isCellDateFormatted(birthCell)) {
						birthDate = birthCell.getDateCellValue();
					} else {
						String dateStr = getCellValueAsString(birthCell).trim();
						if (!dateStr.isEmpty()) {
							try {
								birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
							} catch (ParseException e) {
								try {
									birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
								} catch (ParseException e2) {
									throw new IllegalArgumentException("Ngày sinh dòng " + (i + 1) + " không đúng định dạng (yyyy-MM-dd hoặc dd/MM/yyyy): " + dateStr);
								}
							}
						}
					}
				}

				StudentDTO dto = new StudentDTO();
				dto.setStudentId(studentId);
				dto.setLastName(lastName);
				dto.setFirstName(firstName);
				dto.setBirthDate(birthDate);
				dto.setAddress(address);
				dto.setClassId(classId);
				dtos.add(dto);
			}

			if (dtos.isEmpty()) {
				redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy dữ liệu sinh viên hợp lệ trong tệp Excel.");
			} else {
				studentService.importStudents(dtos);
				redirectAttributes.addFlashAttribute("successMessage", "Nhập danh sách sinh viên từ Excel thành công (Đã nhập " + dtos.size() + " sinh viên).");
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
