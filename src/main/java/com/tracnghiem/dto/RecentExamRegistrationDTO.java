package com.tracnghiem.dto;

import java.util.Date;

public class RecentExamRegistrationDTO {

    private String classId;
    private String className;
    private String subjectId;
    private String subjectName;
    private Short tryNumber;
    private String level;
    private Date examDate;
    private Short numberOfQuestions;
    private Short duration;

    public RecentExamRegistrationDTO() {
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

    public Short getTryNumber() {
        return tryNumber;
    }

    public void setTryNumber(Short tryNumber) {
        this.tryNumber = tryNumber;
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
}
