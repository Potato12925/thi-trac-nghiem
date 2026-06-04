package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tracnghiem.dto.ClassroomDTO;
import com.tracnghiem.entity.Classroom;
import com.tracnghiem.service.ClassroomService;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

    private static final String INDEX_VIEW = "Classroom/Index";
    private static final String REDIRECT_INDEX = "redirect:/classrooms";

    @Autowired
    private ClassroomService classroomService;

    @GetMapping
    public String showClassroomPage(@RequestParam(defaultValue = "1") int page, ModelMap model) {
        prepareClassroomPage(model, page);

        model.addAttribute("classroomDTO", new ClassroomDTO());

        return INDEX_VIEW;
    }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "1") int page,
            @Validated @ModelAttribute("classroomDTO") ClassroomDTO classroomDTO, BindingResult validationResult,
            ModelMap model) {
        if (validationResult.hasErrors()) {
            return renderClassroomPage(model, page);
        }

        try {
            classroomService.addClassroom(classroomDTO);

            return REDIRECT_INDEX;

        } catch (IllegalArgumentException exception) {

            model.addAttribute("errorMessage", exception.getMessage());

            return renderClassroomPage(model, page);
        }
    }

    @PutMapping("/update")
    public String update(@RequestParam(defaultValue = "1") int page,
            @Validated @ModelAttribute("classroomDTO") ClassroomDTO classroomDTO, BindingResult validationResult,
            ModelMap model) {

        if (validationResult.hasErrors()) {
            model.addAttribute("classrooms", classroomService.getAllClassrooms());

            return INDEX_VIEW;

        }

        try {
            classroomService.updateClassroom(classroomDTO);

            return REDIRECT_INDEX;

        } catch (IllegalArgumentException exception) {

            model.addAttribute("errorMessage", exception.getMessage());

            return renderClassroomPage(model, page);
        }
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(defaultValue = "1") int page,
            @Validated @ModelAttribute("classroomDTO") ClassroomDTO classroomDTO, BindingResult errors,
            ModelMap model) {
        if (errors.hasErrors()) {
            model.addAttribute("classrooms", classroomService.getAllClassrooms());

            return INDEX_VIEW;
        }

        try {
            classroomService.deleteClassroom(classroomDTO);
            return REDIRECT_INDEX;

        } catch (IllegalArgumentException exception) {

            model.addAttribute("errorMessage", exception.getMessage());

            return renderClassroomPage(model, page);
        }
    }

	@PostMapping("/save")
	public String save(@RequestParam(defaultValue = "1") int page,
			@RequestParam("actionsData") String actionsData, ModelMap model, RedirectAttributes redirectAttributes) {

		if (actionsData == null || actionsData.trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không có thay đổi nào để ghi.");
			return REDIRECT_INDEX + "?page=" + page;
		}

		try {
			classroomService.savePendingActions(actionsData);
			redirectAttributes.addFlashAttribute("successMessage", "Ghi các thay đổi xuống CSDL thành công.");
			return REDIRECT_INDEX + "?page=" + page;
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ghi dữ liệu: " + ex.getMessage());
			return REDIRECT_INDEX + "?page=" + page;
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống: " + ex.getMessage());
			return REDIRECT_INDEX + "?page=" + page;
		}
	}

    private void prepareClassroomPage(ModelMap model, int page) {
        int pageSize = 10;

        List<Classroom> classrooms = classroomService.getClassrooms(page, pageSize);

        long totalQuestions = classroomService.countClassrooms();

        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

        model.addAttribute("classrooms", classrooms);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    private String renderClassroomPage(ModelMap model, int page) {

        prepareClassroomPage(model, page);

        return INDEX_VIEW;
    }
}
