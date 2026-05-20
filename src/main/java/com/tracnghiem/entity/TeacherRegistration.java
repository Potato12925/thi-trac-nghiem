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

import com.tracnghiem.entity.id.TeacherRegistrationId;

@Entity
@Table(name = "GiaoVien_DangKy")
public class TeacherRegistration {

    @EmbeddedId
    private TeacherRegistrationId id;

    @ManyToOne
    @MapsId("classId")
    @JoinColumn(name = "MALOP")
    private ClassRoom classRoom;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "MAMH")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private Teacher teacher;

    @Column(name = "TRINHDO")
    private String level;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NGAYTHI")
    private Date examDate;

    @Column(name = "SOCAUTHI")
    private Short totalQuestions;

    @Column(name = "THOIGIAN")
    private Short duration;

    public TeacherRegistration() {
    }

    public TeacherRegistrationId getId() {
        return id;
    }

    public void setId(TeacherRegistrationId id) {
        this.id = id;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public Short getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Short totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }
}