package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dao.TeacherDAO;
import com.tracnghiem.entity.Teacher;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {

    @Autowired
    private TeacherDAO giaoVienDAO;

    @GetMapping
    public String index(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "maGV", required = false) String maGV,
            Model model) {

        List<Teacher> teachers;
        if (search != null && !search.trim().isEmpty()) {
            teachers = giaoVienDAO.findByKeyword(search.trim());
        } else {
            teachers = giaoVienDAO.findAll();
        }

        Teacher teacher = null;
        if (maGV != null && !maGV.trim().isEmpty()) {
            teacher = giaoVienDAO.findById(maGV.trim());
        }
        if (teacher == null) {
            teacher = new Teacher();
        }

        model.addAttribute("teachers", teachers);
        model.addAttribute("teacher", teacher);
        model.addAttribute("search", search);

        return "Lecturer/Index";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("maGV") String maGV,
            @RequestParam(value = "ho", required = false) String ho,
            @RequestParam(value = "ten", required = false) String ten,
            @RequestParam(value = "soDT", required = false) String soDT,
            @RequestParam(value = "diaChi", required = false) String diaChi) {

        if (maGV == null || maGV.trim().isEmpty()) {
            return "redirect:/lecturer";
        }

        Teacher teacher = giaoVienDAO.findById(maGV.trim());
        if (teacher == null) {
            teacher = new Teacher();
            teacher.setMaGV(maGV.trim());
            teacher.setHo(ho != null ? ho.trim() : "");
            teacher.setTen(ten != null ? ten.trim() : "");
            teacher.setSoDT(soDT != null ? soDT.trim() : "");
            teacher.setDiaChi(diaChi != null ? diaChi.trim() : "");
            giaoVienDAO.create(teacher);
        } else {
            teacher.setHo(ho != null ? ho.trim() : "");
            teacher.setTen(ten != null ? ten.trim() : "");
            teacher.setSoDT(soDT != null ? soDT.trim() : "");
            teacher.setDiaChi(diaChi != null ? diaChi.trim() : "");
            giaoVienDAO.update(teacher);
        }

        return "redirect:/lecturer?maGV=" + teacher.getMaGV();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("maGV") String maGV) {
        if (maGV != null && !maGV.trim().isEmpty()) {
            Teacher teacher = giaoVienDAO.findById(maGV.trim());
            if (teacher != null) {
                giaoVienDAO.delete(teacher);
            }
        }
        return "redirect:/lecturer";
    }
}
