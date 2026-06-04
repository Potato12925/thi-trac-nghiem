package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dao.LecturerRegistrationDAO;
import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dto.LecturerActionDTO;
import com.tracnghiem.dto.LecturerDTO;
import com.tracnghiem.dto.LecturerHomeDTO;
import com.tracnghiem.dto.LecturerSubjectClassDTO;
import com.tracnghiem.dto.RecentExamRegistrationDTO;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.entity.LecturerRegistration;
import com.tracnghiem.entity.Question;

@Service
public class LecturerService {

    @Autowired
    private LecturerDAO lecturerDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private LecturerRegistrationDAO lecturerRegistrationDAO;

    @Autowired
    private ExamDAO examDAO;

    private Lecturer convertToEntity(LecturerDTO dto) {
        Lecturer lecturer = new Lecturer();

        lecturer.setLecturerId(dto.getLecturerId());
        lecturer.setLastName(dto.getLastName());
        lecturer.setFirstName(dto.getFirstName());
        lecturer.setPhoneNumber(dto.getPhoneNumber());
        lecturer.setAddress(dto.getAddress());
        lecturer.setEmail(dto.getEmail());

        return lecturer;
    }

    public List<Lecturer> getAllLecturers() {

        return lecturerDAO.findAll();
    }

    public List<Lecturer> getLecturers(int page, int pageSize) {
        return lecturerDAO.findPage(page, pageSize);
    }

    public List<Lecturer> getLecturers(int page, int pageSize, String keyword) {
        return lecturerDAO.findPage(page, pageSize, keyword);
    }

    public long countLecturers() {
        return lecturerDAO.countAll();
    }

    public long countLecturers(String keyword) {
        return lecturerDAO.countAll(keyword);
    }

    public Lecturer findLecturerById(String lecturerId) {
        return lecturerDAO.findById(lecturerId);
    }

    @Transactional(readOnly = true)
    public Lecturer getDashboardLecturerProfile(String loginUser) {
        String normalizedLecturerId = normalize(loginUser);
        if (normalizedLecturerId == null) {
            return null;
        }

        Lecturer lecturer = lecturerDAO.findDashboardProfileByLecturerId(normalizedLecturerId);
        return sanitizeLecturerProfile(lecturer);
    }

    @Transactional(readOnly = true)
    public LecturerHomeDTO getLecturerDashboard(String lecturerId) {
        LecturerHomeDTO dashboard = new LecturerHomeDTO();
        dashboard.setLatestWorkingSubjectName("Chưa có dữ liệu");

        String normalizedLecturerId = normalize(lecturerId);
        if (normalizedLecturerId == null) {
            return dashboard;
        }

        dashboard.setTotalQuestions(questionDAO.countByLecturer(normalizedLecturerId));
        dashboard.setTotalRegistrations(lecturerRegistrationDAO.countByLecturer(normalizedLecturerId));
        dashboard.setTotalRegisteredSubjects(lecturerRegistrationDAO.countDistinctSubjectsByLecturer(normalizedLecturerId));
        dashboard.setTotalRegisteredClasses(lecturerRegistrationDAO.countDistinctClassesByLecturer(normalizedLecturerId));
        dashboard.setTotalRelatedExams(examDAO.countByLecturerRegistrations(normalizedLecturerId));
        dashboard.setLatestWorkingSubjectName(resolveLatestWorkingSubjectName(normalizedLecturerId));

        return dashboard;
    }

    @Transactional(readOnly = true)
    public List<RecentExamRegistrationDTO> getRecentExamRegistrations(String lecturerId) {
        String normalizedLecturerId = normalize(lecturerId);
        if (normalizedLecturerId == null) {
            return Collections.emptyList();
        }

        List<LecturerRegistration> registrations = lecturerRegistrationDAO.findRecentByLecturer(normalizedLecturerId, 5);
        List<RecentExamRegistrationDTO> result = new ArrayList<>();

        for (LecturerRegistration registration : registrations) {
            result.add(toRecentExamRegistrationDTO(registration));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<LecturerSubjectClassDTO> getRegisteredSubjectClasses(String lecturerId) {
        String normalizedLecturerId = normalize(lecturerId);
        if (normalizedLecturerId == null) {
            return Collections.emptyList();
        }

        List<Object[]> rows = lecturerRegistrationDAO.findSubjectClassSummaryByLecturer(normalizedLecturerId);
        List<LecturerSubjectClassDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            LecturerSubjectClassDTO dto = new LecturerSubjectClassDTO();
            dto.setClassId(normalize((String) row[0]));
            dto.setClassName(normalize((String) row[1]));
            dto.setSubjectId(normalize((String) row[2]));
            dto.setSubjectName(normalize((String) row[3]));
            dto.setLatestExamDate((java.util.Date) row[4]);
            dto.setRegistrationCount(row[5] == null ? 0L : ((Long) row[5]).longValue());
            result.add(dto);
        }

        return result;
    }

    public List<Lecturer> findLecturerByKeyword(String keyword) {
        return lecturerDAO.findByKeyword(keyword);
    }

    public void addLecturer(LecturerDTO dto) {
        validateLecturerNotExists(dto.getLecturerId());
        Lecturer lecturer = convertToEntity(dto);
        lecturerDAO.create(lecturer);
    }

    public void updateLecturer(LecturerDTO dto) {
        Lecturer lecturer = convertToEntity(dto);
        lecturerDAO.update(lecturer);
    }

    public void deleteLecturer(LecturerDTO dto) {
        Lecturer lecturer = lecturerDAO.findById(dto.getLecturerId());
        if (lecturer == null) {
            throw new IllegalArgumentException("Giảng viên không tồn tại");
        }
        lecturer.setDeleted(true);
        lecturerDAO.update(lecturer);
    }

    private void validateLecturerNotExists(String lecturerId) {
        if (lecturerDAO.existsById(lecturerId)) {
            throw new IllegalArgumentException("Lecturer ID already exists");
        }
    }

	@Transactional
	public void savePendingActions(String actionsData) {
		if (actionsData == null || actionsData.trim().isEmpty()) {
			return;
		}

		List<LecturerActionDTO> actions = new ArrayList<>();
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
			String lecturerId = parts[1].trim();
			String lastName = parts.length > 2 ? parts[2].trim() : "";
			String firstName = parts.length > 3 ? parts[3].trim() : "";
			String phoneNumber = parts.length > 4 ? parts[4].trim() : "";
			String address = parts.length > 5 ? parts[5].trim() : "";
			String email = parts.length > 6 ? parts[6].trim() : "";

			actions.add(new LecturerActionDTO(type, lecturerId, lastName, firstName, phoneNumber, address, email));
		}

		for (LecturerActionDTO action : actions) {
			LecturerDTO dto = new LecturerDTO();
			dto.setLecturerId(action.getLecturerId());
			dto.setLastName(action.getLastName());
			dto.setFirstName(action.getFirstName());
			dto.setPhoneNumber(action.getPhoneNumber());
			dto.setAddress(action.getAddress());
			dto.setEmail(action.getEmail());

			if ("ADD".equals(action.getType())) {
				addLecturer(dto);
			} else if ("UPDATE".equals(action.getType())) {
				updateLecturer(dto);
			} else if ("DELETE".equals(action.getType())) {
				deleteLecturer(dto);
			}
		}
	}

	@Transactional
	public void importLecturers(List<LecturerDTO> dtos) {
		for (LecturerDTO dto : dtos) {
			String lecturerId = dto.getLecturerId();
			String lastName = dto.getLastName();
			String firstName = dto.getFirstName();
			String phoneNumber = dto.getPhoneNumber();
			String address = dto.getAddress();

			if (lecturerId == null || lecturerId.trim().isEmpty()) {
				throw new IllegalArgumentException("Mã giảng viên không được để trống.");
			}
			lecturerId = lecturerId.trim();
			if (lecturerId.length() > 8) {
				throw new IllegalArgumentException("Mã giảng viên không được vượt quá 8 ký tự: " + lecturerId);
			}
			if (!lecturerId.matches("^[A-Z0-9]+$")) {
				throw new IllegalArgumentException("Mã giảng viên chỉ được chứa chữ in hoa và số: " + lecturerId);
			}

			if (lastName == null || lastName.trim().isEmpty()) {
				throw new IllegalArgumentException("Họ giảng viên không được để trống cho mã: " + lecturerId);
			}
			lastName = lastName.trim();
			if (lastName.length() > 40) {
				throw new IllegalArgumentException("Họ giảng viên không được vượt quá 40 ký tự cho mã: " + lecturerId);
			}

			if (firstName == null || firstName.trim().isEmpty()) {
				throw new IllegalArgumentException("Tên giảng viên không được để trống cho mã: " + lecturerId);
			}
			firstName = firstName.trim();
			if (firstName.length() > 10) {
				throw new IllegalArgumentException("Tên giảng viên không được vượt quá 10 ký tự cho mã: " + lecturerId);
			}

			if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
				throw new IllegalArgumentException("Số điện thoại không được để trống cho mã: " + lecturerId);
			}
			phoneNumber = phoneNumber.trim();
			if (phoneNumber.length() > 15) {
				throw new IllegalArgumentException("Số điện thoại không được vượt quá 15 ký tự cho mã: " + lecturerId);
			}
			if (!phoneNumber.matches("^[0-9+\\-\\s]+$")) {
				throw new IllegalArgumentException("Số điện thoại không hợp lệ cho mã: " + lecturerId);
			}

			if (address == null || address.trim().isEmpty()) {
				throw new IllegalArgumentException("Địa chỉ không được để trống cho mã: " + lecturerId);
			}
			address = address.trim();
			if (address.length() > 50) {
				throw new IllegalArgumentException("Địa chỉ không được vượt quá 50 ký tự cho mã: " + lecturerId);
			}

			if (lecturerDAO.existsById(lecturerId)) {
				throw new IllegalArgumentException("Mã giảng viên '" + lecturerId + "' đã tồn tại trong hệ thống.");
			}

			LecturerDTO cleanDto = new LecturerDTO();
			cleanDto.setLecturerId(lecturerId);
			cleanDto.setLastName(lastName);
			cleanDto.setFirstName(firstName);
			cleanDto.setPhoneNumber(phoneNumber);
			cleanDto.setAddress(address);

			addLecturer(cleanDto);
		}
	}

    private Lecturer sanitizeLecturerProfile(Lecturer source) {
        if (source == null) {
            return null;
        }

        Lecturer lecturer = new Lecturer();
        lecturer.setLecturerId(normalize(source.getLecturerId()));
        lecturer.setLastName(normalize(source.getLastName()));
        lecturer.setFirstName(normalize(source.getFirstName()));
        lecturer.setPhoneNumber(normalize(source.getPhoneNumber()));
        lecturer.setAddress(normalize(source.getAddress()));
        lecturer.setEmail(normalize(source.getEmail()));
        lecturer.setDeleted(source.isDeleted());
        return lecturer;
    }

    private RecentExamRegistrationDTO toRecentExamRegistrationDTO(LecturerRegistration registration) {
        RecentExamRegistrationDTO dto = new RecentExamRegistrationDTO();
        dto.setClassId(registration.getClassRoom() == null ? null : normalize(registration.getClassRoom().getClassId()));
        dto.setClassName(registration.getClassRoom() == null ? null : normalize(registration.getClassRoom().getClassName()));
        dto.setSubjectId(registration.getSubject() == null ? null : normalize(registration.getSubject().getSubjectId()));
        dto.setSubjectName(registration.getSubject() == null ? null : normalize(registration.getSubject().getSubjectName()));
        dto.setTryNumber(registration.getId() == null ? null : registration.getId().getTryNumber());
        dto.setLevel(normalize(registration.getLevel()));
        dto.setExamDate(registration.getExamDate());
        dto.setNumberOfQuestions(registration.getNumberOfQuestions());
        dto.setDuration(registration.getDuration());
        return dto;
    }

    private String resolveLatestWorkingSubjectName(String lecturerId) {
        LecturerRegistration latestRegistration = lecturerRegistrationDAO.findLatestByLecturer(lecturerId);
        if (latestRegistration != null && latestRegistration.getSubject() != null) {
            String subjectName = normalize(latestRegistration.getSubject().getSubjectName());
            if (subjectName != null) {
                return subjectName;
            }
        }

        Question latestQuestion = questionDAO.findLatestQuestionByLecturer(lecturerId);
        if (latestQuestion != null && latestQuestion.getSubject() != null) {
            String subjectName = normalize(latestQuestion.getSubject().getSubjectName());
            if (subjectName != null) {
                return subjectName;
            }
        }

        return "Chưa có dữ liệu";
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
