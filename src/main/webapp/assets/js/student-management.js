document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll(".btn-edit").forEach((button) => {
    button.addEventListener("click", function () {
      const row = this.closest("tr");
      // save previous state for undo
      _lastStudentState = getStudentState();
      const b = document.getElementById("btnUndo");
      if (b) b.disabled = false;
      fillFormFromRow(row);
    });
  });
});

function fillFormFromRow(row) {
  const cells = row.querySelectorAll("td");
  document.getElementById("studentId").value = cells[0].innerText.trim();
  document.getElementById("lastName").value = cells[1].innerText.trim();
  document.getElementById("firstName").value = cells[2].innerText.trim();
  document.getElementById("birthDate").value = cells[3].innerText.trim();
  document.getElementById("address").value = cells[4].innerText.trim();
  document.getElementById("classId").value = cells[5].innerText.trim();
  document.getElementById("studentId").readOnly = true;
}

function submitForm(action) {
  const form = document.getElementById("studentForm");
  if (!form) return;
  form.action = (window.APP_BASE_PATH || "") + action;
}

function resetForm() {
  const form = document.getElementById("studentForm");
  if (!form) return;
  form.reset();
  document.getElementById("studentId").readOnly = false;
}

// Undo support
let _lastStudentState = null;

function getStudentState() {
  return {
    studentId: document.getElementById("studentId")?.value || "",
    lastName: document.getElementById("lastName")?.value || "",
    firstName: document.getElementById("firstName")?.value || "",
    birthDate: document.getElementById("birthDate")?.value || "",
    address: document.getElementById("address")?.value || "",
    classId: document.getElementById("classId")?.value || "",
    readOnly: document.getElementById("studentId")?.readOnly || false,
  };
}

function restoreStudentState(state) {
  if (!state) return;
  document.getElementById("studentId").value = state.studentId;
  document.getElementById("lastName").value = state.lastName;
  document.getElementById("firstName").value = state.firstName;
  document.getElementById("birthDate").value = state.birthDate;
  document.getElementById("address").value = state.address;
  document.getElementById("classId").value = state.classId;
  document.getElementById("studentId").readOnly = state.readOnly;
}

const _btnUndoStudent = document.getElementById("btnUndo");
if (_btnUndoStudent) {
  _btnUndoStudent.addEventListener("click", function () {
    restoreStudentState(_lastStudentState);
    _lastStudentState = null;
    this.disabled = true;
  });
}

const _studentForm = document.getElementById("studentForm");
if (_studentForm) {
  _studentForm.addEventListener("submit", function () {
    _lastStudentState = null;
    const b = document.getElementById("btnUndo");
    if (b) b.disabled = true;
  });
}
