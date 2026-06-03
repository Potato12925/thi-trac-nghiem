function populateQuestionForm(tableRow) {
  const tableCells = tableRow.querySelectorAll("td");

  document.getElementById("questionId").value = tableCells[0].innerText.trim();

  document.getElementById("subjectId").value = tableCells[1].innerText.trim();

  document.getElementById("level").value = tableCells[2].innerText.trim();

  document.getElementById("content").value = tableCells[3].innerText.trim();

  document.getElementById("optionA").value = tableCells[4].innerText.trim();

  document.getElementById("optionB").value = tableCells[5].innerText.trim();

  document.getElementById("optionC").value = tableCells[6].innerText.trim();

  document.getElementById("optionD").value = tableCells[7].innerText.trim();

  document.getElementById("correctAnswer").value =
    tableCells[8].innerText.trim();

  document.getElementById("lecturerId").value = tableCells[9].innerText.trim();

  document.getElementById("questionId").readOnly = true;
}

document.querySelectorAll(".btn-edit-question").forEach((button) => {
  button.addEventListener("click", function () {
    const selectedRow = this.closest("tr");

    // save current form state for undo
    _lastQuestionState = getQuestionState();
    const b = document.getElementById("btnUndo");
    if (b) b.disabled = false;

    populateQuestionForm(selectedRow);
  });
});

function configureFormAction(actionPath) {
  const questionForm = document.getElementById("questionForm");
  const basePath = window.APP_BASE_PATH || "";

  questionForm.action = basePath + "/questions/" + actionPath;
}

function clearQuestionForm() {
  document.getElementById("questionForm").reset();

  document.getElementById("questionId").readOnly = false;
}

// Undo support
let _lastQuestionState = null;

function getQuestionState() {
  return {
    questionId: document.getElementById("questionId")?.value || "",
    subjectId: document.getElementById("subjectId")?.value || "",
    level: document.getElementById("level")?.value || "",
    content: document.getElementById("content")?.value || "",
    optionA: document.getElementById("optionA")?.value || "",
    optionB: document.getElementById("optionB")?.value || "",
    optionC: document.getElementById("optionC")?.value || "",
    optionD: document.getElementById("optionD")?.value || "",
    correctAnswer: document.getElementById("correctAnswer")?.value || "",
    lecturerId: document.getElementById("lecturerId")?.value || "",
    readOnly: document.getElementById("questionId")?.readOnly || false,
  };
}

function restoreQuestionState(state) {
  if (!state) return;
  document.getElementById("questionId").value = state.questionId;
  document.getElementById("subjectId").value = state.subjectId;
  document.getElementById("level").value = state.level;
  document.getElementById("content").value = state.content;
  document.getElementById("optionA").value = state.optionA;
  document.getElementById("optionB").value = state.optionB;
  document.getElementById("optionC").value = state.optionC;
  document.getElementById("optionD").value = state.optionD;
  document.getElementById("correctAnswer").value = state.correctAnswer;
  document.getElementById("lecturerId").value = state.lecturerId;
  document.getElementById("questionId").readOnly = state.readOnly;
}

const _btnUndoQuestion = document.getElementById("btnUndo");
if (_btnUndoQuestion) {
  _btnUndoQuestion.addEventListener("click", function () {
    restoreQuestionState(_lastQuestionState);
    _lastQuestionState = null;
    this.disabled = true;
  });
}

const _questionForm = document.getElementById("questionForm");
if (_questionForm) {
  _questionForm.addEventListener("submit", function () {
    _lastQuestionState = null;
    const b = document.getElementById("btnUndo");
    if (b) b.disabled = true;
  });
}
