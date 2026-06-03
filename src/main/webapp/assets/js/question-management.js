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
