package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dao.MonHocDAO;
import com.tracnghiem.entity.MonHoc;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private MonHocDAO monHocDAO;

    @GetMapping
    public String index(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "maMH", required = false) String maMH,
            Model model) {

        List<MonHoc> subjects;

        if (search != null && !search.trim().isEmpty()) {
            subjects = monHocDAO.findByKeyword(search.trim());
        } else {
            subjects = monHocDAO.findAll();
        }

        MonHoc subject = null;
        if (maMH != null && !maMH.trim().isEmpty()) {
            subject = monHocDAO.findById(maMH.trim());
        }
        if (subject == null) {
            subject = new MonHoc();
        }

        model.addAttribute("subjects", subjects);
        model.addAttribute("subject", subject);
        model.addAttribute("search", search);

        return "Subject/Index";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("maMH") String maMH,
            @RequestParam("tenMH") String tenMH) {

        if (maMH == null || maMH.trim().isEmpty()) {
            return "redirect:/subject";
        }

        MonHoc subject = monHocDAO.findById(maMH.trim());
        if (subject == null) {
            subject = new MonHoc();
            subject.setMaMH(maMH.trim());
            subject.setTenMH(tenMH != null ? tenMH.trim() : "");
            monHocDAO.create(subject);
        } else {
            subject.setTenMH(tenMH != null ? tenMH.trim() : "");
            monHocDAO.update(subject);
        }

        return "redirect:/subject?maMH=" + subject.getMaMH();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("maMH") String maMH) {
        if (maMH != null && !maMH.trim().isEmpty()) {
            MonHoc subject = monHocDAO.findById(maMH.trim());
            if (subject != null) {
                monHocDAO.delete(subject);
            }
        }
        return "redirect:/subject";
    }
}
