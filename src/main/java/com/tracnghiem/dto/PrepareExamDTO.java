package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PrepareExamDTO {

    @NotBlank(message = "Class ID is required")
    private String classId;

    @NotBlank(message = "Subject ID is required")
    private String subjectId;

    @NotNull(message = "Try number is required")
    private Short tryNumber;

    public PrepareExamDTO() {
    }

    public PrepareExamDTO(String classId, String subjectId, Short tryNumber) {
        this.classId = classId;
        this.subjectId = subjectId;
        this.tryNumber = tryNumber;
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

    public Short getTryNumber() {
        return tryNumber;
    }

    public void setTryNumber(Short tryNumber) {
        this.tryNumber = tryNumber;
    }
}
