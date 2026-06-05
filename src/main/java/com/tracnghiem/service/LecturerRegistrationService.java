package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.ClassroomDAO;
import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dao.LecturerRegistrationDAO;
import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.dto.LecturerRegistrationDTO;
import com.tracnghiem.entity.Classroom;
import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.entity.LecturerRegistration;
import com.tracnghiem.entity.Question;
import com.tracnghiem.entity.Subject;
import com.tracnghiem.entity.id.LecturerRegistrationId;

@Service
@Transactional
public class LecturerRegistrationService {

    @Autowired
    private LecturerRegistrationDAO lecturerRegistrationDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private ClassroomDAO classroomDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private LecturerDAO lecturerDAO;

    @Autowired
    private ExamDAO examDAO;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void registerExam(LecturerRegistrationDTO dto, String userMaGv, String role) throws Exception {
        // 1. Determine Lecturer ID based on role
        String finalMaGv = "PGV".equals(role) ? dto.getLecturerId() : userMaGv;
        if (finalMaGv == null || finalMaGv.trim().isEmpty()) {
            throw new Exception("Mã giáo viên không được để trống.");
        }

        // 2. Check if already exists
        LecturerRegistrationId id = new LecturerRegistrationId(dto.getClassId(), dto.getSubjectId(),
                dto.getTryNumber());
        if (lecturerRegistrationDAO.findById(id) != null) {
            throw new Exception("Lịch thi cho Lớp, Môn và Lần thi này đã tồn tại.");
        }

        // 3. Question distribution logic & selection
        int soCau = dto.getNumberOfQuestions();
        String trinhDo = dto.getLevel();
        String maMh = dto.getSubjectId();

        // Check if enough questions exist and can generate a valid exam
        List<Question> selectedQuestions = generateExamQuestions(maMh, trinhDo, soCau);
        if (selectedQuestions.size() != soCau) {
            throw new Exception("Không thể tạo đủ số lượng câu hỏi yêu cầu.");
        }

        // 4. Save entity
        Classroom classRoom = classroomDAO.findById(dto.getClassId());
        Subject subject = subjectDAO.findById(dto.getSubjectId());
        Lecturer Lecturer = lecturerDAO.findById(finalMaGv);

        if (classRoom == null || subject == null || Lecturer == null) {
            throw new Exception("Dữ liệu Lớp, Môn Học hoặc Giáo Viên không hợp lệ trong hệ thống.");
        }

        LecturerRegistration entity = new LecturerRegistration();
        entity.setId(id);
        entity.setClassRoom(classRoom);
        entity.setSubject(subject);
        entity.setLecturer(Lecturer);

        entity.setLevel(trinhDo);
        entity.setExamDate(dto.getExamDate());
        entity.setNumberOfQuestions(dto.getNumberOfQuestions());
        entity.setDuration(dto.getDuration());
        entity.setQuestions(selectedQuestions);

        lecturerRegistrationDAO.create(entity);
    }

    public void updateExam(LecturerRegistrationDTO dto, String userMaGv, String role) throws Exception {
        LecturerRegistration entity = lecturerRegistrationDAO.findExamByKeys(dto.getClassId(), dto.getSubjectId(), dto.getTryNumber());

        if (entity == null) {
            throw new Exception("Không tìm thấy lịch thi này.");
        }

        List<Exam> takenExams = examDAO.findClassExams(dto.getClassId(), dto.getSubjectId(), dto.getTryNumber());
        if (takenExams != null && !takenExams.isEmpty()) {
            throw new Exception("Lịch thi này đã có thí sinh thi, không được chỉnh sửa.");
        }

        if (!"PGV".equals(role) && !entity.getLecturer().getLecturerId().equals(userMaGv)) {
            throw new Exception("Bạn không có quyền cập nhật lịch thi của người khác.");
        }

        if ("PGV".equals(role) && dto.getLecturerId() != null && !dto.getLecturerId().trim().isEmpty()) {
            Lecturer lecturer = lecturerDAO.findById(dto.getLecturerId());
            if (lecturer == null) {
                throw new Exception("Giáo viên không hợp lệ.");
            }
            entity.setLecturer(lecturer);
        }

        boolean recreateQuestions = false;
        if (!entity.getLevel().equals(dto.getLevel()) || !entity.getNumberOfQuestions().equals(dto.getNumberOfQuestions())) {
            recreateQuestions = true;
        }

        entity.setLevel(dto.getLevel());
        entity.setExamDate(dto.getExamDate());
        entity.setDuration(dto.getDuration());

        if (recreateQuestions) {
            entity.setNumberOfQuestions(dto.getNumberOfQuestions());
            List<Question> selectedQuestions = generateExamQuestions(dto.getSubjectId(), dto.getLevel(), dto.getNumberOfQuestions());
            if (selectedQuestions.size() != dto.getNumberOfQuestions()) {
                throw new Exception("Không thể tạo đủ số lượng câu hỏi yêu cầu.");
            }
            entity.setQuestions(selectedQuestions);
        }

        lecturerRegistrationDAO.update(entity);

        // Xóa cache Redis khi cập nhật lịch thi để tránh dữ liệu cũ
        String redisKey = "exam_questions:" + dto.getClassId() + ":" + dto.getSubjectId() + ":" + dto.getTryNumber();
        try {
            redisTemplate.delete(redisKey);
        } catch (Exception e) {
            System.err.println("Failed to evict Redis cache: " + e.getMessage());
        }
    }

    public void deleteExam(String maLop, String maMh, Short lan, String userMaGv, String role) throws Exception {
        LecturerRegistration entity = lecturerRegistrationDAO.findExamByKeys(maLop, maMh, lan);

        if (entity == null) {
            throw new Exception("Không tìm thấy lịch thi này.");
        }

        // PGV can delete any, GV can only delete their own
        if (!"PGV".equals(role) && !entity.getLecturer().getLecturerId().equals(userMaGv)) {
            throw new Exception("Bạn không có quyền xóa lịch thi của người khác.");
        }

        lecturerRegistrationDAO.delete(entity);

        // Xóa cache Redis khi xóa lịch thi
        String redisKey = "exam_questions:" + maLop + ":" + maMh + ":" + lan;
        try {
            redisTemplate.delete(redisKey);
        } catch (Exception e) {
            System.err.println("Failed to evict Redis cache: " + e.getMessage());
        }
    }

    public List<LecturerRegistration> getRegistrations(String lecturerId, String role) {
        if ("PGV".equals(role)) {
            return lecturerRegistrationDAO.findAll();
        } else {
            return lecturerRegistrationDAO.findByLecturer(lecturerId);
        }
    }

    public List<Question> generateExamQuestions(String maMh, String trinhDo, int soCau) throws Exception {
        int soCauToiThieu = (int) Math.ceil(0.7 * soCau);

        long totalA = questionDAO.countAvailableQuestions(maMh, "A");
        long totalB = questionDAO.countAvailableQuestions(maMh, "B");
        long totalC = questionDAO.countAvailableQuestions(maMh, "C");

        List<Question> examQuestions = new ArrayList<>();

        if ("A".equals(trinhDo)) {
            if (totalA < soCauToiThieu) {
                throw new Exception("Không đủ số câu hỏi mức A. Cần ít nhất " + soCauToiThieu
                        + " câu mức A (70%), hiện có " + totalA + " câu.");
            }

            int takeA = (int) Math.min(totalA, soCau);
            int takeB = soCau - takeA;

            if (takeB > totalB) {
                throw new Exception(
                        "Không đủ câu hỏi bù mức B. Cần bù " + takeB + " câu B nhưng kho chỉ có " + totalB + " câu.");
            }

            examQuestions.addAll(questionDAO.getRandomQuestions(maMh, "A", takeA));
            if (takeB > 0) {
                examQuestions.addAll(questionDAO.getRandomQuestions(maMh, "B", takeB));
            }
        } else if ("B".equals(trinhDo)) {
            if (totalB < soCauToiThieu) {
                throw new Exception("Không đủ số câu hỏi mức B. Cần ít nhất " + soCauToiThieu
                        + " câu mức B (70%), hiện có " + totalB + " câu.");
            }
            int takeB = (int) Math.min(totalB, soCau);
            int takeC = soCau - takeB;
            if (takeC > totalC) {
                throw new Exception(
                        "Không đủ câu hỏi bù mức C. Cần bù " + takeC + " câu C nhưng kho chỉ có " + totalC + " câu.");
            }
            examQuestions.addAll(questionDAO.getRandomQuestions(maMh, "B", takeB));
            if (takeC > 0) {
                examQuestions.addAll(questionDAO.getRandomQuestions(maMh, "C", takeC));
            }
        } else if ("C".equals(trinhDo)) {
            if (totalC < soCau) {
                throw new Exception(
                        "Không đủ số câu hỏi mức C. Cần " + soCau + " câu mức C, hiện có " + totalC + " câu.");
            }
            examQuestions.addAll(questionDAO.getRandomQuestions(maMh, "C", soCau));
        } else {
            throw new Exception("Trình độ không hợp lệ.");
        }

        Collections.shuffle(examQuestions);
        return examQuestions;
    }
}
