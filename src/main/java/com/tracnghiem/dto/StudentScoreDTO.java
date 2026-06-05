package com.tracnghiem.dto;

public class StudentScoreDTO {
    private String studentId;
    private String lastName;
    private String firstName;
    private Float score;
    private String letterGrade;
    private Integer examId;
    private Boolean isViolation;

    public StudentScoreDTO() {
    }

    public StudentScoreDTO(String studentId, String lastName, String firstName, Float score, String letterGrade) {
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.score = score;
        this.letterGrade = letterGrade;
    }

    public StudentScoreDTO(String studentId, String lastName, String firstName, Float score, String letterGrade, Integer examId, Boolean isViolation) {
        this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.score = score;
        this.letterGrade = letterGrade;
        this.examId = examId;
        this.isViolation = isViolation;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public Boolean getIsViolation() {
        return isViolation;
    }

    public void setIsViolation(Boolean isViolation) {
        this.isViolation = isViolation;
    }
}
