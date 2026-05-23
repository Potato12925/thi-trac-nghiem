package com.tracnghiem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tracnghiem.entity.id.ScoreId;

@Entity
@Table(name = "BANGDIEM")
public class Score {

    @EmbeddedId
    private ScoreId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "MASV")
    private Student student;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "MAMH")
    private Subject subject;

    @Temporal(TemporalType.DATE)
    @Column(name = "NGAYTHI")
    private Date examDate;

    @Column(name = "DIEM")
    private Float score;

    public Score() {
    }

    public ScoreId getId() {
        return id;
    }

    public void setId(ScoreId id) {
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
}