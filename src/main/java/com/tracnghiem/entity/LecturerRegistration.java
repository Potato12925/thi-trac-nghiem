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
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import java.util.List;

import com.tracnghiem.entity.id.LecturerRegistrationId;

@Entity
@Table(name = "GIAOVIEN_DANGKY")
public class LecturerRegistration {

    @EmbeddedId
    private LecturerRegistrationId id;

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
    private Lecturer lecturer;

    @Column(name = "TRINHDO")
    private String level;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NGAYTHI")
    private Date examDate;

    @Column(name = "SOCAUTHI")
    private Short numberOfQuestions;

    @Column(name = "THOIGIAN")
    private Short duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "CT_GIAOVIEN_DANGKY",
        joinColumns = {
            @JoinColumn(name = "MALOP", referencedColumnName = "MALOP"),
            @JoinColumn(name = "MAMH", referencedColumnName = "MAMH"),
            @JoinColumn(name = "LAN", referencedColumnName = "LAN")
        },
        inverseJoinColumns = @JoinColumn(name = "CAUHOI", referencedColumnName = "CAUHOI")
    )
    private List<Question> questions;

    public LecturerRegistration() {
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public LecturerRegistrationId getId() {
        return id;
    }

    public void setId(LecturerRegistrationId id) {
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

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
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

    public Short getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Short numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Short getDuration() {
        return duration;
    }

    public void setDuration(Short duration) {
        this.duration = duration;
    }
}
