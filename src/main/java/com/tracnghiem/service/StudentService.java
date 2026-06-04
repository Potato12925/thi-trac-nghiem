package com.tracnghiem.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.LecturerRegistrationDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.StudentActionDTO;
import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.dto.StudentDashboardDTO;
import com.tracnghiem.dto.UpcomingExamDTO;
import com.tracnghiem.entity.Classroom;
import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.LecturerRegistration;
import com.tracnghiem.entity.Student;

@Service
public class StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
	private AuthService authService;

	@Autowired
	private ClassroomService classroomService;

    @Autowired
    private ExamDAO examDAO;

    @Autowired
    private LecturerRegistrationDAO lecturerRegistrationDAO;

    private Student convertToEntity(StudentDTO dto) {
        Classroom classRoom = classroomService.findClassroomById(dto.getClassId());

        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setLastName(dto.getLastName());
        student.setFirstName(dto.getFirstName());
        student.setBirthDate(dto.getBirthDate());
        student.setAddress(dto.getAddress());
        student.setEmail(dto.getEmail());
        student.setClassRoom(classRoom);

        return student;
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public List<Student> getStudents(int page, int pageSize) {
        return studentDAO.findPage(page, pageSize);
    }

    public List<Student> getStudents(int page, int pageSize, String keyword, String classId) {
        return studentDAO.findPage(page, pageSize, keyword, classId);
    }

    public long countStudents() {
        return studentDAO.countAll();
    }

    public long countStudents(String keyword, String classId) {
        return studentDAO.countAll(keyword, classId);
    }

    public Student getStudentById(String studentId) {
        return studentDAO.findById(studentId);
    }

    public void addStudent(StudentDTO dto) {
        Student student = convertToEntity(dto);
        studentDAO.create(student);
    }

    public void updateStudent(StudentDTO dto) {
        Student student = convertToEntity(dto);
        studentDAO.update(student);
    }

    public void deleteStudent(StudentDTO dto) {
        Student student = studentDAO.findById(dto.getStudentId());
        if (student == null) {
            throw new IllegalArgumentException("Sinh viên không tồn tại");
        }
        student.setDeleted(true);
        studentDAO.update(student);

        authService.deleteAccount(dto.getStudentId());
    }

    private void ensureStudentNotExists(String studentId) {
        if (studentDAO.existsById(studentId)) {
            throw new IllegalArgumentException("MÃ£ sinh viÃªn Ä‘Ã£ tá»“n táº¡i");
        }
    }

    @Transactional
    public void addStudentWithAccount(StudentDTO dto) {
        ensureStudentNotExists(dto.getStudentId());
        authService.createAccount(dto.getStudentId());
        addStudent(dto);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Student getDashboardStudentProfile(String loginUser) {
        String normalizedStudentId = normalize(loginUser);
        if (normalizedStudentId == null) {
            return null;
        }

        Student student = studentDAO.findDashboardProfileByStudentId(normalizedStudentId);
        return sanitizeStudentProfile(student);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public StudentDashboardDTO getStudentDashboardStats(Student student, Date now) {
        StudentDashboardDTO dashboard = new StudentDashboardDTO();
        dashboard.setAverageScore(0.0d);
        dashboard.setLatestExamSubjectName("Chưa có dữ liệu");

        if (student == null) {
            return dashboard;
        }

        String studentId = normalize(student.getStudentId());
        dashboard.setCompletedSubjectCount(examDAO.countDistinctCompletedSubjectsByStudent(studentId));

        Double averageScore = examDAO.findAverageScoreByStudent(studentId);
        if (averageScore != null) {
            dashboard.setAverageScore(averageScore);
        }

        Exam latestCompletedExam = examDAO.findLatestCompletedExamByStudent(studentId);
        dashboard.setLatestExamSubjectName(resolveLatestSubjectName(latestCompletedExam));

        List<UpcomingExamDTO> upcomingExams = getUpcomingExams(student, now);
        dashboard.setPendingExamCount(upcomingExams.size());

        return dashboard;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<UpcomingExamDTO> getUpcomingExams(Student student, Date now) {
        if (student == null || student.getClassRoom() == null) {
            return Collections.emptyList();
        }

        String studentId = normalize(student.getStudentId());
        String classId = normalize(student.getClassRoom().getClassId());
        if (studentId == null || classId == null) {
            return Collections.emptyList();
        }

        List<LecturerRegistration> registrations = lecturerRegistrationDAO.findUpcomingExamsForStudent(studentId, classId, now);
        return registrations.stream()
                .map(this::toUpcomingExamDTO)
                .collect(Collectors.toList());
    }

    private Student sanitizeStudentProfile(Student source) {
        if (source == null) {
            return null;
        }

        Student student = new Student();
        student.setStudentId(normalize(source.getStudentId()));
        student.setLastName(normalize(source.getLastName()));
        student.setFirstName(normalize(source.getFirstName()));
        student.setBirthDate(source.getBirthDate());
        student.setAddress(normalize(source.getAddress()));
        student.setEmail(normalize(source.getEmail()));

        if (source.getClassRoom() != null) {
            Classroom classRoom = new Classroom();
            classRoom.setClassId(normalize(source.getClassRoom().getClassId()));
            classRoom.setClassName(normalize(source.getClassRoom().getClassName()));
            student.setClassRoom(classRoom);
        }

        return student;
    }

    private UpcomingExamDTO toUpcomingExamDTO(LecturerRegistration registration) {
        UpcomingExamDTO dto = new UpcomingExamDTO();
        dto.setSubjectId(registration.getSubject() == null ? null : normalize(registration.getSubject().getSubjectId()));
        dto.setSubjectName(registration.getSubject() == null ? null : normalize(registration.getSubject().getSubjectName()));
        dto.setExamDate(registration.getExamDate());
        dto.setTryNumber(registration.getId() == null ? null : registration.getId().getTryNumber());
        dto.setLevel(normalize(registration.getLevel()));
        dto.setDuration(registration.getDuration());
        return dto;
    }

    private String resolveLatestSubjectName(Exam latestCompletedExam) {
        if (latestCompletedExam == null || latestCompletedExam.getSubject() == null) {
            return "Chưa có dữ liệu";
        }

        String subjectName = normalize(latestCompletedExam.getSubject().getSubjectName());
        return subjectName == null ? "Chưa có dữ liệu" : subjectName;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    @Transactional
    public void savePendingActions(String actionsData) {
        if (actionsData == null || actionsData.trim().isEmpty()) {
            return;
        }

        List<StudentActionDTO> actions = new ArrayList<>();
        String[] lines = actionsData.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(":::", -1);
            if (parts.length < 2) {
                continue;
            }
            String type = parts[0].trim();
            String studentId = parts[1].trim();
            String lastName = parts.length > 2 ? parts[2].trim() : "";
            String firstName = parts.length > 3 ? parts[3].trim() : "";
            String birthDate = parts.length > 4 ? parts[4].trim() : "";
            String address = parts.length > 5 ? parts[5].trim() : "";
            String classId = parts.length > 6 ? parts[6].trim() : "";

            actions.add(new StudentActionDTO(type, studentId, lastName, firstName, birthDate, address, classId));
        }

        for (StudentActionDTO action : actions) {
            StudentDTO dto = new StudentDTO();
            dto.setStudentId(action.getStudentId());
            dto.setLastName(action.getLastName());
            dto.setFirstName(action.getFirstName());
            dto.setBirthDate(parseBirthDate(action.getBirthDate()));
            dto.setAddress(action.getAddress());
            dto.setClassId(action.getClassId());

            if ("ADD".equals(action.getType())) {
                addStudentWithAccount(dto);
            } else if ("UPDATE".equals(action.getType())) {
                updateStudent(dto);
            } else if ("DELETE".equals(action.getType())) {
                deleteStudent(dto);
            }
        }
    }

	private Date parseBirthDate(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Ngày sinh không đúng định dạng yyyy-MM-dd: " + dateStr, e);
		}
	}

	@Transactional
	public void importStudents(List<StudentDTO> dtos) {
		for (StudentDTO dto : dtos) {
			String studentId = dto.getStudentId();
			String lastName = dto.getLastName();
			String firstName = dto.getFirstName();
			Date birthDate = dto.getBirthDate();
			String address = dto.getAddress();
			String classId = dto.getClassId();

			if (studentId == null || studentId.trim().isEmpty()) {
				throw new IllegalArgumentException("Mã sinh viên không được để trống.");
			}
			studentId = studentId.trim();
			if (studentId.length() != 8) {
				throw new IllegalArgumentException("Mã sinh viên phải chứa đúng 8 ký tự: " + studentId);
			}
			if (!studentId.matches("^[A-Z0-9]+$")) {
				throw new IllegalArgumentException("Mã sinh viên chỉ được chứa chữ in hoa và số: " + studentId);
			}

			if (lastName == null || lastName.trim().isEmpty()) {
				throw new IllegalArgumentException("Họ sinh viên không được để trống cho mã: " + studentId);
			}
			lastName = lastName.trim();
			if (lastName.length() > 50) {
				throw new IllegalArgumentException("Họ sinh viên không được vượt quá 50 ký tự cho mã: " + studentId);
			}

			if (firstName == null || firstName.trim().isEmpty()) {
				throw new IllegalArgumentException("Tên sinh viên không được để trống cho mã: " + studentId);
			}
			firstName = firstName.trim();
			if (firstName.length() > 10) {
				throw new IllegalArgumentException("Tên sinh viên không được vượt quá 10 ký tự cho mã: " + studentId);
			}

			if (birthDate == null) {
				throw new IllegalArgumentException("Ngày sinh không được để trống cho mã: " + studentId);
			}
			if (birthDate.after(new Date())) {
				throw new IllegalArgumentException("Ngày sinh phải ở trong quá khứ cho mã: " + studentId);
			}

			if (address == null || address.trim().isEmpty()) {
				throw new IllegalArgumentException("Địa chỉ không được để trống cho mã: " + studentId);
			}
			address = address.trim();
			if (address.length() > 100) {
				throw new IllegalArgumentException("Địa chỉ không được vượt quá 100 ký tự cho mã: " + studentId);
			}

			if (classId == null || classId.trim().isEmpty()) {
				throw new IllegalArgumentException("Mã lớp không được để trống cho mã: " + studentId);
			}
			classId = classId.trim();
			if (classroomService.findClassroomById(classId) == null) {
				throw new IllegalArgumentException("Mã lớp '" + classId + "' không tồn tại trong hệ thống (Mã SV: " + studentId + ").");
			}

			if (studentDAO.existsById(studentId)) {
				throw new IllegalArgumentException("Mã sinh viên '" + studentId + "' đã tồn tại trong hệ thống.");
			}

			StudentDTO cleanDto = new StudentDTO();
			cleanDto.setStudentId(studentId);
			cleanDto.setLastName(lastName);
			cleanDto.setFirstName(firstName);
			cleanDto.setBirthDate(birthDate);
			cleanDto.setAddress(address);
			cleanDto.setClassId(classId);

			addStudentWithAccount(cleanDto);
		}
	}
}
