package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BoDe")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAUHOI")
    private Integer questionId;

    @ManyToOne
    @JoinColumn(name = "MAMH")
    private Subject subject;

    @Column(name = "TRINHDO")
    private String level;

    @Column(name = "NOIDUNG")
    private String content;

    @Column(name = "A")
    private String optionA;

    @Column(name = "B")
    private String optionB;

    @Column(name = "C")
    private String optionC;

    @Column(name = "D")
    private String optionD;

    @Column(name = "DAP_AN")
    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private Lecturer teacher;

    public Question() {
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Lecturer getTeacher() {
        return teacher;
    }

    public void setTeacher(Lecturer teacher) {
        this.teacher = teacher;
    }
}