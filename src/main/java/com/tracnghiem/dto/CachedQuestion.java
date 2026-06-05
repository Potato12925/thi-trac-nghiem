package com.tracnghiem.dto;

import java.io.Serializable;

public class CachedQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer questionId;
    private String content;
    private String level;
    private String subjectId;

    public CachedQuestion() {}

    public CachedQuestion(Integer questionId, String content, String level, String subjectId) {
        this.questionId = questionId;
        this.content = content;
        this.level = level;
        this.subjectId = subjectId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
