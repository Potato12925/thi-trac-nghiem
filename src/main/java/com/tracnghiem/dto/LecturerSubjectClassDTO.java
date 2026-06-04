package com.tracnghiem.dto;

import java.util.Date;

public class LecturerSubjectClassDTO {

    private String classId;
    private String className;
    private String subjectId;
    private String subjectName;
    private Date latestExamDate;
    private long registrationCount;

    public LecturerSubjectClassDTO() {
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

    public Date getLatestExamDate() {
        return latestExamDate;
    }

    public void setLatestExamDate(Date latestExamDate) {
        this.latestExamDate = latestExamDate;
    }

    public long getRegistrationCount() {
        return registrationCount;
    }

    public void setRegistrationCount(long registrationCount) {
        this.registrationCount = registrationCount;
    }
}
