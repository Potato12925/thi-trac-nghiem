package com.tracnghiem.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class LecturerRegistrationDTO {

    @NotBlank(message = "Vui lòng chọn lớp")
    private String classId;

    @NotBlank(message = "Vui lòng chọn môn học")
    private String subjectId;

    @NotBlank(message = "Vui lòng chọn trình độ")
    private String level;

    @NotNull(message = "Vui lòng chọn lần thi")
    @Min(value = 1, message = "Lần thi phải là 1 hoặc 2")
    @Max(value = 2, message = "Lần thi phải là 1 hoặc 2")
    private Short tryNumber;

    @NotNull(message = "Vui lòng nhập số câu thi")
    @Min(value = 10, message = "Số câu thi tối thiểu là 10")
    @Max(value = 100, message = "Số câu thi tối đa là 100")
    private Short numberOfQuestions;

    @NotNull(message = "Vui lòng nhập thời gian thi")
    @Min(value = 5, message = "Thời gian thi tối thiểu là 5 phút")
    @Max(value = 60, message = "Thời gian thi tối đa là 60 phút")
    private Short duration;

    @NotNull(message = "Vui lòng chọn ngày thi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date examDate;

    private String lecturerId;

    public LecturerRegistrationDTO() {
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Short getTryNumber() {
        return tryNumber;
    }

    public void setTryNumber(Short tryNumber) {
        this.tryNumber = tryNumber;
    }

    public Short getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Short numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}
