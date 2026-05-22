package com.tracnghiem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dao.TeacherDAO;
import com.tracnghiem.dao.TeacherRegistrationDAO;
import com.tracnghiem.dao.ClassRoomDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.dto.ExamRegistrationDTO;
import com.tracnghiem.entity.Teacher;
import com.tracnghiem.entity.TeacherRegistration;
import com.tracnghiem.entity.ClassRoom;
import com.tracnghiem.entity.Subject;
import com.tracnghiem.entity.id.TeacherRegistrationId;
import com.tracnghiem.service.ExamRegistrationService;

@Service
@Transactional
public class ExamRegistrationServiceImpl implements ExamRegistrationService {

    @Autowired
    private TeacherRegistrationDAO teacherRegistrationDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private ClassRoomDAO classRoomDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private TeacherDAO teacherDAO;

    @Override
    public void registerExam(ExamRegistrationDTO dto, String userMaGv, String role) throws Exception {
        // 1. Determine teacher ID based on role
        String finalMaGv = "PGV".equals(role) ? dto.getTeacherId() : userMaGv;
        if (finalMaGv == null || finalMaGv.trim().isEmpty()) {
            throw new Exception("Mã giáo viên không được để trống.");
        }

        // 2. Check if already exists
        TeacherRegistrationId id = new TeacherRegistrationId(dto.getClassId(), dto.getSubjectId(), dto.getTryNumber());
        if (teacherRegistrationDAO.findById(id) != null) {
            throw new Exception("Lịch thi cho Lớp, Môn và Lần thi này đã tồn tại.");
        }

        // 3. Question distribution logic
        int soCau = dto.getNumberOfQuestions();
        int soCauToiThieu = (int) Math.ceil(0.7 * soCau);
        String trinhDo = dto.getLevel();
        String maMh = dto.getSubjectId();

        long totalA = questionDAO.countAvailableQuestions(maMh, "A");
        long totalB = questionDAO.countAvailableQuestions(maMh, "B");
        long totalC = questionDAO.countAvailableQuestions(maMh, "C");

        if ("A".equals(trinhDo)) {
            if (totalA < soCauToiThieu) {
                throw new Exception("Không đủ số câu hỏi mức A. Cần ít nhất " + soCauToiThieu
                        + " câu mức A (70%), hiện có " + totalA + " câu.");
            }
            long takeA = Math.min(totalA, soCau);
            long takeB = soCau - takeA;
            if (takeB > totalB) {
                throw new Exception(
                        "Không đủ câu hỏi bù mức B. Cần bù " + takeB + " câu B nhưng kho chỉ có " + totalB + " câu.");
            }
        } else if ("B".equals(trinhDo)) {
            if (totalB < soCauToiThieu) {
                throw new Exception("Không đủ số câu hỏi mức B. Cần ít nhất " + soCauToiThieu
                        + " câu mức B (70%), hiện có " + totalB + " câu.");
            }
            long takeB = Math.min(totalB, soCau);
            long takeC = soCau - takeB;
            if (takeC > totalC) {
                throw new Exception(
                        "Không đủ câu hỏi bù mức C. Cần bù " + takeC + " câu C nhưng kho chỉ có " + totalC + " câu.");
            }
        } else if ("C".equals(trinhDo)) {
            if (totalC < soCau) {
                throw new Exception(
                        "Không đủ số câu hỏi mức C. Cần " + soCau + " câu mức C, hiện có " + totalC + " câu.");
            }
        } else {
            throw new Exception("Trình độ không hợp lệ.");
        }

        // 4. Save entity
        ClassRoom classRoom = classRoomDAO.findById(dto.getClassId());
        Subject subject = subjectDAO.findById(dto.getSubjectId());
        Teacher teacher = teacherDAO.findById(finalMaGv);

        if (classRoom == null || subject == null || teacher == null) {
            throw new Exception("Dữ liệu Lớp, Môn Học hoặc Giáo Viên không hợp lệ trong hệ thống.");
        }

        TeacherRegistration entity = new TeacherRegistration();
        entity.setId(id);
        entity.setLop(classRoom);
        entity.setMonHoc(subject);
        entity.setGiaoVien(teacher);

        entity.setTrinhDo(trinhDo);
        entity.setNgayThi(dto.getExamDate());
        entity.setSoCauThi(dto.getNumberOfQuestions());
        entity.setThoiGian(dto.getDuration());

        teacherRegistrationDAO.create(entity);
    }

    @Override
    public void deleteExam(String maLop, String maMh, Short lan, String userMaGv, String role) throws Exception {
        TeacherRegistrationId id = new TeacherRegistrationId(maLop, maMh, lan);
        TeacherRegistration entity = teacherRegistrationDAO.findById(id);

        if (entity == null) {
            throw new Exception("Không tìm thấy lịch thi này.");
        }

        // PGV can delete any, GV can only delete their own
        if (!"PGV".equals(role) && !entity.getGiaoVien().getTeacherId().equals(userMaGv)) {
            throw new Exception("Bạn không có quyền xóa lịch thi của người khác.");
        }

        teacherRegistrationDAO.delete(entity);
    }

    @Override
    public List<TeacherRegistration> getRegistrations(String userMaGv, String role) {
        if ("PGV".equals(role)) {
            return teacherRegistrationDAO.findAll();
        } else {
            return teacherRegistrationDAO.findByTeacher(userMaGv);
        }
    }
}
