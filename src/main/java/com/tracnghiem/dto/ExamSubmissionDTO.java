package com.tracnghiem.dto;

import java.util.Map;

public class ExamSubmissionDTO {
    private Map<Integer, String> answers;
    private Boolean isViolation;

    public Map<Integer, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, String> answers) {
        this.answers = answers;
    }

    public Boolean getIsViolation() {
        return isViolation;
    }

    public void setIsViolation(Boolean isViolation) {
        this.isViolation = isViolation;
    }
}
