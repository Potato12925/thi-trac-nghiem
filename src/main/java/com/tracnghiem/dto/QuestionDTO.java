package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class QuestionDTO {
	private Integer questionId;

	@NotBlank(message = "Subject ID is required")
	@Size(max = 5, message = "Subject ID must contain exactly 5 characters")
	@Pattern(regexp = "^[A-Z0-9]+$", message = "Subject ID can only contain uppercase letters and numbers")
	private String subjectId;

	@NotBlank(message = "Level is required")
	@Pattern(regexp = "^[ABC]$", message = "Level must be A, B, or C")
	private String level;

	@NotBlank(message = "Question content is required")
	@Size(max = 200, message = "Question content must not exceed 200 characters")
	private String content;

	@NotBlank(message = "Option A is required")
	@Size(max = 250, message = "Option A must not exceed 250 characters")
	private String optionA;

	@NotBlank(message = "Option B is required")
	@Size(max = 250, message = "Option B must not exceed 250 characters")
	private String optionB;

	@NotBlank(message = "Option C is required")
	@Size(max = 250, message = "Option C must not exceed 250 characters")
	private String optionC;

	@NotBlank(message = "Option D is required")
	@Size(max = 250, message = "Option D must not exceed 250 characters")
	private String optionD;

	@NotBlank(message = "Correct answer is required")
	@Pattern(regexp = "^[ABCD]$", message = "Correct answer must be A, B, C, or D")
	private String correctAnswer;

	@NotBlank(message = "Lecturer ID is required")
	@Size(max = 8, message = "Lecturer ID must contain exactly 8 characters")
	@Pattern(regexp = "^[A-Z0-9]+$", message = "Lecturer ID can only contain uppercase letters and numbers")
	private String lecturerId;

	public QuestionDTO() {
		super();
	}

	public QuestionDTO(Integer questionId,
			@NotBlank(message = "Subject ID is required") @Size(max = 5, message = "Subject ID must contain exactly 5 characters") @Pattern(regexp = "^[A-Z0-9]+$", message = "Subject ID can only contain uppercase letters and numbers") String subjectId,
			@NotBlank(message = "Level is required") @Pattern(regexp = "^[ABC]$", message = "Level must be A, B, or C") String level,
			@NotBlank(message = "Question content is required") @Size(max = 200, message = "Question content must not exceed 200 characters") String content,
			@NotBlank(message = "Option A is required") @Size(max = 250, message = "Option A must not exceed 250 characters") String optionA,
			@NotBlank(message = "Option B is required") @Size(max = 250, message = "Option B must not exceed 250 characters") String optionB,
			@NotBlank(message = "Option C is required") @Size(max = 250, message = "Option C must not exceed 250 characters") String optionC,
			@NotBlank(message = "Option D is required") @Size(max = 250, message = "Option D must not exceed 250 characters") String optionD,
			@NotBlank(message = "Correct answer is required") @Pattern(regexp = "^[ABCD]$", message = "Correct answer must be A, B, C, or D") String correctAnswer,
			@NotBlank(message = "Lecturer ID is required") @Size(max = 8, message = "Lecturer ID must contain exactly 8 characters") @Pattern(regexp = "^[A-Z0-9]+$", message = "Lecturer ID can only contain uppercase letters and numbers") String lecturerId) {
		super();
		this.questionId = questionId;
		this.subjectId = subjectId;
		this.level = level;
		this.content = content;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.correctAnswer = correctAnswer;
		this.lecturerId = lecturerId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}
}
