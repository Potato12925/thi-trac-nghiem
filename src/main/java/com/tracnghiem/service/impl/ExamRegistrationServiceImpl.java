package com.tracnghiem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.BoDeDAO;
import com.tracnghiem.dao.GiaoVienDAO;
import com.tracnghiem.dao.GiaoVienDangKyDAO;
import com.tracnghiem.dao.LopDAO;
import com.tracnghiem.dao.MonHocDAO;
import com.tracnghiem.dto.ExamRegistrationDTO;
import com.tracnghiem.entity.GiaoVien;
import com.tracnghiem.entity.GiaoVienDangKy;
import com.tracnghiem.entity.Lop;
import com.tracnghiem.entity.MonHoc;
import com.tracnghiem.entity.id.GiaoVienDangKyId;
import com.tracnghiem.service.ExamRegistrationService;

@Service
@Transactional
public class ExamRegistrationServiceImpl implements ExamRegistrationService {

    @Autowired
    private GiaoVienDangKyDAO giaoVienDangKyDAO;

    @Autowired
    private BoDeDAO boDeDAO;

    @Autowired
    private LopDAO lopDAO;

    @Autowired
    private MonHocDAO monHocDAO;

    @Autowired
    private GiaoVienDAO giaoVienDAO;

    @Override
    public void registerExam(ExamRegistrationDTO dto, String userMaGv, String role) throws Exception {
        // 1. Determine teacher ID based on role
        String finalMaGv = "PGV".equals(role) ? dto.getMaGv() : userMaGv;
        if (finalMaGv == null || finalMaGv.trim().isEmpty()) {
            throw new Exception("Mã giáo viên không được để trống.");
        }

        // 2. Check if already exists
        GiaoVienDangKyId id = new GiaoVienDangKyId(dto.getMaLop(), dto.getMaMh(), dto.getLan());
        if (giaoVienDangKyDAO.findById(id) != null) {
            throw new Exception("Lịch thi cho Lớp, Môn và Lần thi này đã tồn tại.");
        }

        // 3. Question distribution logic
        int soCau = dto.getSoCauThi();
        int soCauToiThieu = (int) Math.ceil(0.7 * soCau);
        String trinhDo = dto.getTrinhDo();
        String maMh = dto.getMaMh();

        long totalA = boDeDAO.countAvailableQuestions(maMh, "A");
        long totalB = boDeDAO.countAvailableQuestions(maMh, "B");
        long totalC = boDeDAO.countAvailableQuestions(maMh, "C");

        if ("A".equals(trinhDo)) {
            if (totalA < soCauToiThieu) {
                throw new Exception("Không đủ số câu hỏi mức A. Cần ít nhất " + soCauToiThieu + " câu mức A (70%), hiện có " + totalA + " câu.");
            }
            long takeA = Math.min(totalA, soCau);
            long takeB = soCau - takeA;
            if (takeB > totalB) {
                throw new Exception("Không đủ câu hỏi bù mức B. Cần bù " + takeB + " câu B nhưng kho chỉ có " + totalB + " câu.");
            }
        } else if ("B".equals(trinhDo)) {
            if (totalB < soCauToiThieu) {
                throw new Exception("Không đủ số câu hỏi mức B. Cần ít nhất " + soCauToiThieu + " câu mức B (70%), hiện có " + totalB + " câu.");
            }
            long takeB = Math.min(totalB, soCau);
            long takeC = soCau - takeB;
            if (takeC > totalC) {
                throw new Exception("Không đủ câu hỏi bù mức C. Cần bù " + takeC + " câu C nhưng kho chỉ có " + totalC + " câu.");
            }
        } else if ("C".equals(trinhDo)) {
            if (totalC < soCau) {
                throw new Exception("Không đủ số câu hỏi mức C. Cần " + soCau + " câu mức C, hiện có " + totalC + " câu.");
            }
        } else {
            throw new Exception("Trình độ không hợp lệ.");
        }

        // 4. Save entity
        Lop lop = lopDAO.findById(dto.getMaLop());
        MonHoc monHoc = monHocDAO.findById(dto.getMaMh());
        GiaoVien giaoVien = giaoVienDAO.findById(finalMaGv);

        if (lop == null || monHoc == null || giaoVien == null) {
            throw new Exception("Dữ liệu Lớp, Môn Học hoặc Giáo Viên không hợp lệ trong hệ thống.");
        }

        GiaoVienDangKy entity = new GiaoVienDangKy();
        entity.setId(id);
        entity.setLop(lop);
        entity.setMonHoc(monHoc);
        entity.setGiaoVien(giaoVien);
        
        entity.setTrinhDo(trinhDo);
        entity.setNgayThi(dto.getNgayThi());
        entity.setSoCauThi(dto.getSoCauThi());
        entity.setThoiGian(dto.getThoiGian());

        giaoVienDangKyDAO.create(entity);
    }

    @Override
    public void deleteExam(String maLop, String maMh, Short lan, String userMaGv, String role) throws Exception {
        GiaoVienDangKyId id = new GiaoVienDangKyId(maLop, maMh, lan);
        GiaoVienDangKy entity = giaoVienDangKyDAO.findById(id);
        
        if (entity == null) {
            throw new Exception("Không tìm thấy lịch thi này.");
        }

        // PGV can delete any, GV can only delete their own
        if (!"PGV".equals(role) && !entity.getGiaoVien().getMaGV().equals(userMaGv)) {
            throw new Exception("Bạn không có quyền xóa lịch thi của người khác.");
        }

        giaoVienDangKyDAO.delete(entity);
    }

    @Override
    public List<GiaoVienDangKy> getRegistrations(String userMaGv, String role) {
        if ("PGV".equals(role)) {
            return giaoVienDangKyDAO.findAll();
        } else {
            return giaoVienDangKyDAO.findByTeacher(userMaGv);
        }
    }
}
