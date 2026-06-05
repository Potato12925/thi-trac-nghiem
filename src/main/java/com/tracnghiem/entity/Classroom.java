package com.tracnghiem.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LOP")
public class Classroom {

	@Id
	@Column(name = "MALOP", length = 15)
	private String classId;

	@Column(name = "TENLOP", nullable = false, unique = true)
	private String className;

	@OneToMany(mappedBy = "classRoom")
	private List<Student> students;

	@Column(name = "IS_DELETED", nullable = false)
	private boolean deleted = false;

	public Classroom() {
		super();
	}

	public Classroom(String classId, String className, List<Student> students) {
		super();
		this.classId = classId;
		this.className = className;
		this.students = students;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getClassDisplayName() {
		return this.classId + " - " + this.className;
	}

}