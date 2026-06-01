package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.tracnghiem.entity.id.ExamDetailId;

@Entity
@Table(name = "CHITIETBAITHI")
public class ExamDetail {

    @EmbeddedId
    private ExamDetailId id;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "BAITHI_ID")
    private Exam exam;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "CAUHOI")
    private Question question;

    @Column(name = "DAPAN_SV")
    private String studentAnswer;

    @Column(name = "THUTU")
    private Integer questionOrder;

    public ExamDetail() {
    }

    public ExamDetailId getId() {
        return id;
    }

    public void setId(ExamDetailId id) {
        this.id = id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }
}