document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll(".btn-edit").forEach((button) => {
    button.addEventListener("click", function () {
      const row = this.closest("tr");
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
