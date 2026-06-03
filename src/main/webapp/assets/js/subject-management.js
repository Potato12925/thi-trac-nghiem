document.addEventListener("DOMContentLoaded", function () {
  const editButtons = document.querySelectorAll(".btn-edit");
  const deleteButtons = document.querySelectorAll(".btn-delete");
  const confirmDeleteButton = document.getElementById("confirmDeleteButton");

  editButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const row = this.closest("tr");
      // save state for undo
      _lastSubjectState = getSubjectState();
      const b = document.getElementById("btnUndo");
      if (b) b.disabled = false;
      fillFormFromRow(row);
      setActiveRow(row);
    });
  });

  deleteButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const row = this.closest("tr");
      openDeleteModal(row);
    });
  });

  if (confirmDeleteButton) {
    confirmDeleteButton.addEventListener("click", submitDelete);
  }
});

let activeRow = null;
let deleteModal = null;

function fillFormFromRow(row) {
  const cells = row.querySelectorAll("td");
  const subjectId = cells[0].innerText.trim();
  const subjectName = cells[1].innerText.trim();

  document.getElementById("subjectId").value = subjectId;
  document.getElementById("subjectName").value = subjectName;
  document.getElementById("subjectId").readOnly = true;
  setActiveRow(row);
}

function setActiveRow(row) {
  if (activeRow) {
    activeRow.classList.remove("table-primary");
  }
  activeRow = row;
  row.classList.add("table-primary");
}

function openDeleteModal(row) {
  fillFormFromRow(row);
  const subjectName = row.querySelectorAll("td")[1].innerText.trim();
  document.getElementById("subjectDeleteModalBody").innerText =
    `Bạn có chắc muốn xóa môn học "${subjectName}" không?`;

  if (!deleteModal) {
    deleteModal = new bootstrap.Modal(
      document.getElementById("subjectDeleteModal"),
    );
  }

  deleteModal.show();
}

function submitDelete() {
  const form = document.getElementById("subjectForm");
  if (!form) return;
  form.action = (window.APP_BASE_PATH || "") + "/subjects/delete";
  form.submit();
}

function submitForm(action) {
  const form = document.getElementById("subjectForm");
  if (!form) return;
  form.action = (window.APP_BASE_PATH || "") + "/subjects/" + action;
}

function resetForm() {
  const form = document.getElementById("subjectForm");
  if (!form) return;
  form.reset();
  document.getElementById("subjectId").readOnly = false;
  if (activeRow) {
    activeRow.classList.remove("table-primary");
    activeRow = null;
  }
}

// Undo support
let _lastSubjectState = null;

function getSubjectState() {
  return {
    subjectId: document.getElementById("subjectId")?.value || "",
    subjectName: document.getElementById("subjectName")?.value || "",
    readOnly: document.getElementById("subjectId")?.readOnly || false,
  };
}

function restoreSubjectState(state) {
  if (!state) return;
  document.getElementById("subjectId").value = state.subjectId;
  document.getElementById("subjectName").value = state.subjectName;
  document.getElementById("subjectId").readOnly = state.readOnly;
}

const _btnUndoSubject = document.getElementById("btnUndo");
if (_btnUndoSubject) {
  _btnUndoSubject.addEventListener("click", function () {
    restoreSubjectState(_lastSubjectState);
    _lastSubjectState = null;
    this.disabled = true;
  });
}

const _subjectForm = document.getElementById("subjectForm");
if (_subjectForm) {
  _subjectForm.addEventListener("submit", function () {
    _lastSubjectState = null;
    const b = document.getElementById("btnUndo");
    if (b) b.disabled = true;
  });
}
