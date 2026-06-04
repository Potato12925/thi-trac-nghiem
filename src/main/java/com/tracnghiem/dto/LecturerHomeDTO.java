package com.tracnghiem.dto;

public class LecturerHomeDTO {

    private long totalQuestions;
    private long totalRegistrations;
    private long totalRegisteredSubjects;
    private long totalRegisteredClasses;
    private long totalRelatedExams;
    private String latestWorkingSubjectName;

    public LecturerHomeDTO() {
    }

    public long getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(long totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public long getTotalRegistrations() {
        return totalRegistrations;
    }

    public void setTotalRegistrations(long totalRegistrations) {
        this.totalRegistrations = totalRegistrations;
    }

    public long getTotalRegisteredSubjects() {
        return totalRegisteredSubjects;
    }

    public void setTotalRegisteredSubjects(long totalRegisteredSubjects) {
        this.totalRegisteredSubjects = totalRegisteredSubjects;
    }

    public long getTotalRegisteredClasses() {
        return totalRegisteredClasses;
    }

    public void setTotalRegisteredClasses(long totalRegisteredClasses) {
        this.totalRegisteredClasses = totalRegisteredClasses;
    }

    public long getTotalRelatedExams() {
        return totalRelatedExams;
    }

    public void setTotalRelatedExams(long totalRelatedExams) {
        this.totalRelatedExams = totalRelatedExams;
    }

    public String getLatestWorkingSubjectName() {
        return latestWorkingSubjectName;
    }

    public void setLatestWorkingSubjectName(String latestWorkingSubjectName) {
        this.latestWorkingSubjectName = latestWorkingSubjectName;
    }
}
