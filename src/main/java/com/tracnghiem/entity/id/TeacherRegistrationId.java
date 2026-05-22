package com.tracnghiem.entity.id;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TeacherRegistrationId implements Serializable {

    @Column(name = "MALOP")
    private String classId;

    @Column(name = "MAMH")
    private String subjectId;

    @Column(name = "LAN")
    private Short tryNumber;

    public TeacherRegistrationId() {
    }

    public TeacherRegistrationId(String classId, String subjectId, Short tryNumber) {
        this.classId = classId;
        this.subjectId = subjectId;
        this.tryNumber = tryNumber;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Short getTryNumber() {
        return tryNumber;
    }

    public void setTryNumber(Short tryNumber) {
        this.tryNumber = tryNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tryNumber, classId, subjectId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof TeacherRegistrationId)) {
            return false;
        }

        TeacherRegistrationId other = (TeacherRegistrationId) obj;

        return Objects.equals(classId, other.classId)
                && Objects.equals(subjectId, other.subjectId)
                && Objects.equals(tryNumber, other.tryNumber);
    }
}