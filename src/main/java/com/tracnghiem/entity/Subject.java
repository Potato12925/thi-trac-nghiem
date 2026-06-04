package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MONHOC")
public class Subject {

    @Id
    @Column(name = "MAMH", length = 5)
    private String subjectId;

    @Column(name = "TENMH", nullable = false, unique = true)
    private String subjectName;

    @Column(name = "IS_DELETED", nullable = false)
    private boolean deleted = false;

    public Subject() {
        super();
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

	public String getSubjectDisplayName() {
		return this.subjectId + " - " + this.subjectName;
	}

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
