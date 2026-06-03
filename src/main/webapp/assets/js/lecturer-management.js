document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll(".btn-edit").forEach((button) => {
    button.addEventListener("click", function () {
      const row = this.closest("tr");
      // save previous state for undo
      _lastLecturerState = getLecturerState();
      const b = document.getElementById("btnUndo");
      if (b) b.disabled = false;
      fillFormFromRow(row);
      enableActionButtons();
    });
  });
});

function fillFormFromRow(row) {
  document.getElementById("lecturerId").value = row.dataset.id || "";
  document.getElementById("lastName").value = row.dataset.lastname || "";
  document.getElementById("firstName").value = row.dataset.firstname || "";
  document.getElementById("phoneNumber").value = row.dataset.phone || "";
  document.getElementById("address").value = row.dataset.address || "";
  document.getElementById("lecturerId").readOnly = true;
}

function enableActionButtons() {
  const updateButton = document.getElementById("btnUpdate");
  const deleteButton = document.getElementById("btnDelete");
  if (updateButton) updateButton.disabled = false;
  if (deleteButton) deleteButton.disabled = false;
}

function resetForm() {
  const form = document.getElementById("lecturerForm");
  if (!form) return;
  form.reset();
  document.getElementById("lecturerId").readOnly = false;
  const updateButton = document.getElementById("btnUpdate");
  const deleteButton = document.getElementById("btnDelete");
  if (updateButton) updateButton.disabled = true;
  if (deleteButton) deleteButton.disabled = true;
}

// Undo support
let _lastLecturerState = null;

function getLecturerState() {
  return {
    lecturerId: document.getElementById("lecturerId")?.value || "",
    lastName: document.getElementById("lastName")?.value || "",
    firstName: document.getElementById("firstName")?.value || "",
    phoneNumber: document.getElementById("phoneNumber")?.value || "",
    address: document.getElementById("address")?.value || "",
    readOnly: document.getElementById("lecturerId")?.readOnly || false,
  };
}

function restoreLecturerState(state) {
  if (!state) return;
  document.getElementById("lecturerId").value = state.lecturerId;
  document.getElementById("lastName").value = state.lastName;
  document.getElementById("firstName").value = state.firstName;
  document.getElementById("phoneNumber").value = state.phoneNumber;
  document.getElementById("address").value = state.address;
  document.getElementById("lecturerId").readOnly = state.readOnly;
}

const _btnUndoLecturer = document.getElementById("btnUndo");
if (_btnUndoLecturer) {
  _btnUndoLecturer.addEventListener("click", function () {
    restoreLecturerState(_lastLecturerState);
    _lastLecturerState = null;
    this.disabled = true;
  });
}

// clear undo when form submits
const _lecturerForm = document.getElementById("lecturerForm");
if (_lecturerForm) {
  _lecturerForm.addEventListener("submit", function () {
    _lastLecturerState = null;
    const b = document.getElementById("btnUndo");
    if (b) b.disabled = true;
  });
}
