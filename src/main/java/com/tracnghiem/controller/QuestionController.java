package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dao.BoDeDAO;
import com.tracnghiem.dao.GiaoVienDAO;
import com.tracnghiem.dao.MonHocDAO;
import com.tracnghiem.entity.BoDe;
import com.tracnghiem.entity.GiaoVien;
import com.tracnghiem.entity.MonHoc;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private BoDeDAO boDeDAO;

    @Autowired
    private MonHocDAO monHocDAO;

    @Autowired
    private GiaoVienDAO giaoVienDAO;

    @GetMapping
    public String index(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "cauHoi", required = false) Integer cauHoi,
            Model model) {

        List<BoDe> questions;
        if (search != null && !search.trim().isEmpty()) {
            questions = boDeDAO.findByKeyword(search.trim());
        } else {
            questions = boDeDAO.findAll();
        }

        BoDe selected = null;
        if (cauHoi != null) {
            selected = boDeDAO.findById(cauHoi);
        }
        if (selected == null) {
            selected = new BoDe();
        }

        List<MonHoc> subjects = monHocDAO.findAll();
        List<GiaoVien> teachers = giaoVienDAO.findAll();

        model.addAttribute("questions", questions);
        model.addAttribute("question", selected);
        model.addAttribute("subjects", subjects);
        model.addAttribute("teachers", teachers);
        model.addAttribute("search", search);

        return "Question/Index";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam(value = "cauHoi", required = false) Integer cauHoi,
            @RequestParam("maMH") String maMH,
            @RequestParam("trinhDo") String trinhDo,
            @RequestParam("noiDung") String noiDung,
            @RequestParam(value = "A", required = false) String answerA,
            @RequestParam(value = "B", required = false) String answerB,
            @RequestParam(value = "C", required = false) String answerC,
            @RequestParam(value = "D", required = false) String answerD,
            @RequestParam(value = "dapAn", required = false) String dapAn,
            @RequestParam("maGV") String maGV) {

        MonHoc subject = monHocDAO.findById(maMH.trim());
        GiaoVien teacher = giaoVienDAO.findById(maGV.trim());

        if (subject == null || teacher == null) {
            return "redirect:/question";
        }

        BoDe boDe = null;
        if (cauHoi != null) {
            boDe = boDeDAO.findById(cauHoi);
        }
        if (boDe == null) {
            boDe = new BoDe();
        }

        boDe.setMonHoc(subject);
        boDe.setTrinhDo(trinhDo != null ? trinhDo.trim() : "");
        boDe.setNoiDung(noiDung != null ? noiDung.trim() : "");
        boDe.setA(answerA != null ? answerA.trim() : "");
        boDe.setB(answerB != null ? answerB.trim() : "");
        boDe.setC(answerC != null ? answerC.trim() : "");
        boDe.setD(answerD != null ? answerD.trim() : "");
        boDe.setDapAn(dapAn != null ? dapAn.trim() : "");
        boDe.setGiaoVien(teacher);

        if (boDe.getCauHoi() == null) {
            boDeDAO.create(boDe);
        } else {
            boDeDAO.update(boDe);
        }

        return "redirect:/question?cauHoi=" + boDe.getCauHoi();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("cauHoi") Integer cauHoi) {
        if (cauHoi != null) {
            BoDe boDe = boDeDAO.findById(cauHoi);
            if (boDe != null) {
                boDeDAO.delete(boDe);
            }
        }
        return "redirect:/question";
    }
}
