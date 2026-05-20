package com.tracnghiem.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "BaiThi")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MASV")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "MAMH")
    private Subject subject;

    @Column(name = "LAN")
    private Short attempt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NGAYTHI")
    private Date examDate;

    @Column(name = "DIEM")
    private Float score;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "THOIGIANBATDAU")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "THOIGIANKETTHUC")
    private Date endTime;

    @OneToMany(mappedBy = "exam")
    private List<ExamDetail> examDetails;

    public Exam() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Short getAttempt() {
        return attempt;
    }

    public void setAttempt(Short attempt) {
        this.attempt = attempt;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<ExamDetail> getExamDetails() {
        return examDetails;
    }

    public void setExamDetails(List<ExamDetail> examDetails) {
        this.examDetails = examDetails;
    }
}