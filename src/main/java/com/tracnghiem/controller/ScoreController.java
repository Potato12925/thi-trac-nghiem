package com.tracnghiem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dto.PrepareExamDTO;
import com.tracnghiem.service.ClassroomService;
import com.tracnghiem.service.SubjectService;
import com.tracnghiem.service.ScoreService;
import com.tracnghiem.entity.Classroom;
import com.tracnghiem.entity.Subject;

@Controller
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private SubjectService subjectService;

    private boolean isAuthorized(HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        return role != null && (role.equals("PGV") || role.equals("GIAOVIEN"));
    }

    @GetMapping
    public String index(
            @RequestParam(value = "classId", required = false) String classId,
            @RequestParam(value = "subjectId", required = false) String subjectId,
            @RequestParam(value = "tryNumber", required = false) Short tryNumber,
            ModelMap model,
            HttpSession session) {

        if (!isAuthorized(session)) {
            return "redirect:/auth/login";
        }

        model.addAttribute("dsLop", classroomService.getAllClassrooms());
        model.addAttribute("dsMonHoc", subjectService.getAllSubjects());
        model.addAttribute("scoreFilter", new PrepareExamDTO(classId, subjectId, tryNumber));

        // Pass selected parameters back to form
        model.addAttribute("selectedClassId", classId);
        model.addAttribute("selectedSubjectId", subjectId);
        model.addAttribute("selectedTryNumber", tryNumber);

        if (classId != null && !classId.trim().isEmpty() 
                && subjectId != null && !subjectId.trim().isEmpty() 
                && tryNumber != null) {
            
            Classroom classRoom = classroomService.findClassroomById(classId);
            Subject subject = subjectService.getSubjectById(subjectId);

            model.addAttribute("selectedClassName", classRoom != null ? classRoom.getClassName() : classId);
            model.addAttribute("selectedSubjectName", subject != null ? subject.getSubjectName() : subjectId);
            
            model.addAttribute("scores", scoreService.getClassScores(classId, subjectId, tryNumber));
            model.addAttribute("hasSearched", true);
        } else {
            model.addAttribute("hasSearched", false);
        }

        return "Score/Index";
    }
}
