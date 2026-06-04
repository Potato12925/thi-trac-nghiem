package com.tracnghiem.dto;

import java.util.Date;

public class UpcomingExamDTO {

    private String subjectId;
    private String subjectName;
    private Date examDate;
    private Short tryNumber;
    private String level;
    private Short duration;

    public UpcomingExamDTO() {
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

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
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

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }
}
