document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll(".btn-edit").forEach((button) => {
    button.addEventListener("click", function () {
      const row = this.closest("tr");
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
