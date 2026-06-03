document.addEventListener("DOMContentLoaded", function () {
  const editButtons = document.querySelectorAll(".btn-edit");
  const deleteButtons = document.querySelectorAll(".btn-delete");
  const confirmDeleteButton = document.getElementById("confirmDeleteButton");

  editButtons.forEach((button) => {
    button.addEventListener("click", function () {
      const row = this.closest("tr");
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
