package com.tracnghiem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dao.LopDAO;
import com.tracnghiem.dao.SinhVienDAO;
import com.tracnghiem.entity.Lop;
import com.tracnghiem.entity.SinhVien;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private LopDAO lopDAO;

    @Autowired
    private SinhVienDAO sinhVienDAO;

    @GetMapping
    public String index(
            @RequestParam(value = "classSearch", required = false) String classSearch,
            @RequestParam(value = "studentSearch", required = false) String studentSearch,
            @RequestParam(value = "selectedClass", required = false) String selectedClass,
            @RequestParam(value = "maSV", required = false) String maSV,
            Model model) {

        List<Lop> classes;
        if (classSearch != null && !classSearch.trim().isEmpty()) {
            classes = lopDAO.findByKeyword(classSearch.trim());
        } else {
            classes = lopDAO.findAll();
        }

        Lop currentClass = null;
        if (selectedClass != null && !selectedClass.trim().isEmpty()) {
            currentClass = lopDAO.findById(selectedClass.trim());
        }
        if (currentClass == null && !classes.isEmpty()) {
            currentClass = classes.get(0);
        }
        if (currentClass == null) {
            currentClass = new Lop();
        }

        List<SinhVien> students = new ArrayList<>();
        if (currentClass.getMaLop() != null && !currentClass.getMaLop().trim().isEmpty()) {
            if (studentSearch != null && !studentSearch.trim().isEmpty()) {
                students = sinhVienDAO.findByLopAndKeyword(currentClass.getMaLop(), studentSearch.trim());
            } else {
                students = sinhVienDAO.findByLop(currentClass.getMaLop());
            }
        }

        SinhVien currentStudent = null;
        if (maSV != null && !maSV.trim().isEmpty()) {
            currentStudent = sinhVienDAO.findById(maSV.trim());
        }
        if (currentStudent == null) {
            currentStudent = new SinhVien();
            currentStudent.setLop(currentClass);
        }

        model.addAttribute("classes", classes);
        model.addAttribute("selectedClass", currentClass);
        model.addAttribute("students", students);
        model.addAttribute("student", currentStudent);
        model.addAttribute("classSearch", classSearch);
        model.addAttribute("studentSearch", studentSearch);

        return "Student/Index";
    }

    @PostMapping("/class/save")
    public String saveClass(
            @RequestParam("maLop") String maLop,
            @RequestParam("tenLop") String tenLop) {

        if (maLop == null || maLop.trim().isEmpty()) {
            return "redirect:/student";
        }

        Lop lop = lopDAO.findById(maLop.trim());
        if (lop == null) {
            lop = new Lop();
            lop.setMaLop(maLop.trim());
            lop.setTenLop(tenLop != null ? tenLop.trim() : "");
            lopDAO.create(lop);
        } else {
            lop.setTenLop(tenLop != null ? tenLop.trim() : "");
            lopDAO.update(lop);
        }

        return "redirect:/student?selectedClass=" + lop.getMaLop();
    }

    @PostMapping("/class/delete")
    public String deleteClass(@RequestParam("maLop") String maLop) {
        if (maLop != null && !maLop.trim().isEmpty()) {
            Lop lop = lopDAO.findById(maLop.trim());
            if (lop != null) {
                lopDAO.delete(lop);
            }
        }
        return "redirect:/student";
    }

    @PostMapping("/student/save")
    public String saveStudent(
            @RequestParam("maSV") String maSV,
            @RequestParam("ho") String ho,
            @RequestParam("ten") String ten,
            @RequestParam(value = "ngaySinh", required = false) String ngaySinh,
            @RequestParam(value = "diaChi", required = false) String diaChi,
            @RequestParam("maLop") String maLop) {

        if (maSV == null || maSV.trim().isEmpty() || maLop == null || maLop.trim().isEmpty()) {
            return "redirect:/student";
        }

        Lop lop = lopDAO.findById(maLop.trim());
        if (lop == null) {
            return "redirect:/student";
        }

        SinhVien sinhVien = sinhVienDAO.findById(maSV.trim());
        if (sinhVien == null) {
            sinhVien = new SinhVien();
            sinhVien.setMaSV(maSV.trim());
        }

        sinhVien.setHo(ho != null ? ho.trim() : "");
        sinhVien.setTen(ten != null ? ten.trim() : "");
        sinhVien.setDiaChi(diaChi != null ? diaChi.trim() : "");
        sinhVien.setLop(lop);

        if (ngaySinh != null && !ngaySinh.trim().isEmpty()) {
            try {
                Date parsed = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinh);
                sinhVien.setNgaySinh(parsed);
            } catch (ParseException ignored) {
            }
        }

        if (sinhVienDAO.findById(maSV.trim()) == null) {
            sinhVienDAO.create(sinhVien);
        } else {
            sinhVienDAO.update(sinhVien);
        }

        return "redirect:/student?selectedClass=" + lop.getMaLop() + "&maSV=" + sinhVien.getMaSV();
    }

    @PostMapping("/student/delete")
    public String deleteStudent(
            @RequestParam("maSV") String maSV,
            @RequestParam(value = "selectedClass", required = false) String selectedClass) {

        if (maSV != null && !maSV.trim().isEmpty()) {
            SinhVien sinhVien = sinhVienDAO.findById(maSV.trim());
            if (sinhVien != null) {
                sinhVienDAO.delete(sinhVien);
            }
        }

        if (selectedClass != null && !selectedClass.trim().isEmpty()) {
            return "redirect:/student?selectedClass=" + selectedClass.trim();
        }

        return "redirect:/student";
    }
}
