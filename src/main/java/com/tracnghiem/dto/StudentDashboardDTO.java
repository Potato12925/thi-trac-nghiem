package com.tracnghiem.dto;

public class StudentDashboardDTO {

    private long completedSubjectCount;
    private int pendingExamCount;
    private Double averageScore;
    private String latestExamSubjectName;

    public StudentDashboardDTO() {
    }

    public long getCompletedSubjectCount() {
        return completedSubjectCount;
    }

    public void setCompletedSubjectCount(long completedSubjectCount) {
        this.completedSubjectCount = completedSubjectCount;
    }

    public int getPendingExamCount() {
        return pendingExamCount;
    }

    public void setPendingExamCount(int pendingExamCount) {
        this.pendingExamCount = pendingExamCount;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public String getLatestExamSubjectName() {
        return latestExamSubjectName;
    }

    public void setLatestExamSubjectName(String latestExamSubjectName) {
        this.latestExamSubjectName = latestExamSubjectName;
    }
}
