package com.tracnghiem.dto;

import java.util.Date;

public class RecentExamRegistrationDTO {

    private String lecturerId;
    private String lecturerName;
    private String classId;
    private String className;
    private String subjectId;
    private String subjectName;
    private Short attempt;
    private String level;
    private Date examDate;
    private Short questionCount;
    private Short duration;

    public RecentExamRegistrationDTO() {
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Short getAttempt() {
        return attempt;
    }

    public void setAttempt(Short attempt) {
        this.attempt = attempt;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Short getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Short questionCount) {
        this.questionCount = questionCount;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }

    public Short getTryNumber() {
        return attempt;
    }

    public void setTryNumber(Short tryNumber) {
        this.attempt = tryNumber;
    }

    public Short getNumberOfQuestions() {
        return questionCount;
    }

    public void setNumberOfQuestions(Short numberOfQuestions) {
        this.questionCount = numberOfQuestions;
    }
}
