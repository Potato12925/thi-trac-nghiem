package com.tracnghiem.entity.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ScoreId implements Serializable {

    private String studentId;

    private String subjectId;

    private Short tryNumber;

    // equals hashCode
}