package com.tracnghiem.entity.id;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ExamDetailId implements Serializable {

    @Column(name = "BAITHI_ID")
    private Integer examId;

    @Column(name = "CAUHOI")
    private Integer questionId;

    public ExamDetailId() {
    }

    public ExamDetailId(Integer examId, Integer questionId) {
        this.examId = examId;
        this.questionId = questionId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamDetailId that = (ExamDetailId) o;
        return Objects.equals(examId, that.examId) && Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, questionId);
    }
}
