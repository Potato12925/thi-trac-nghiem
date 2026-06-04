document.addEventListener("DOMContentLoaded", function () {
  const btnAdd = document.getElementById("btnAdd");
  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  const btnUndo = document.getElementById("btnUndo");
  const btnSave = document.getElementById("btnSave");
  const btnReset = document.getElementById("btnReset");
  const tableBody = document.querySelector(".table-responsive table tbody");

  // Add click listeners to form buttons
  if (btnAdd) btnAdd.addEventListener("click", handleLocalAdd);
  if (btnUpdate) btnUpdate.addEventListener("click", handleLocalUpdate);
  if (btnDelete) btnDelete.addEventListener("click", handleLocalDelete);
  if (btnUndo) btnUndo.addEventListener("click", handleUndo);
  if (btnSave) btnSave.addEventListener("click", handleSave);
  if (btnReset) btnReset.addEventListener("click", resetForm);

  // Use event delegation on table body for Edit button
  if (tableBody) {
    tableBody.addEventListener("click", function (e) {
      const editBtn = e.target.closest(".btn-edit");
      if (editBtn) {
        const row = editBtn.closest("tr");
        fillFormFromRow(row);
        enableActionButtons();
        hideClientError();
      }
    });
  }

  // Warn about unsaved changes when navigating away
  window.addEventListener("beforeunload", function (e) {
    if (pendingActions.length > 0) {
      e.preventDefault();
      e.returnValue = "Bạn có thay đổi chưa được ghi xuống CSDL. Bạn có chắc chắn muốn rời đi?";
      return e.returnValue;
    }
  });
});

let pendingActions = [];
let activeRow = null;

function findRowById(studentId) {
  const rows = document.querySelectorAll(".table-responsive table tbody tr");
  for (let row of rows) {
    const cells = row.querySelectorAll("td");
    if (cells.length > 0 && cells[0].innerText.trim() === studentId) {
      return row;
    }
  }
  return null;
}

function fillFormFromRow(row) {
  const cells = row.querySelectorAll("td");
  document.getElementById("studentId").value = cells[0].innerText.trim();
  document.getElementById("lastName").value = cells[1].innerText.trim();
  document.getElementById("firstName").value = cells[2].innerText.trim();
  document.getElementById("birthDate").value = cells[3].innerText.trim();
  document.getElementById("address").value = cells[4].innerText.trim();
  document.getElementById("email").value = cells[5].innerText.trim();
  document.getElementById("classId").value = cells[6].innerText.trim();
  document.getElementById("studentId").readOnly = true;
  setActiveRow(row);
}

function setActiveRow(row) {
  if (activeRow) {
    activeRow.classList.remove("table-primary");
  }
  activeRow = row;
  row.classList.add("table-primary");
}

function enableActionButtons() {
  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  if (btnUpdate) btnUpdate.disabled = false;
  if (btnDelete) btnDelete.disabled = false;
}

function resetForm() {
  const form = document.getElementById("studentForm");
  if (form) form.reset();

  const studentIdInput = document.getElementById("studentId");
  if (studentIdInput) {
    studentIdInput.readOnly = false;
  }

  if (activeRow) {
    activeRow.classList.remove("table-primary");
    activeRow = null;
  }

  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  if (btnUpdate) btnUpdate.disabled = true;
  if (btnDelete) btnDelete.disabled = true;

  hideClientError();
}

function showClientError(msg) {
  const alertDiv = document.getElementById("clientAlert");
  if (alertDiv) {
    alertDiv.innerText = msg;
    alertDiv.classList.remove("d-none");
    window.scrollTo({ top: 0, behavior: "smooth" });
  }
}

function hideClientError() {
  const alertDiv = document.getElementById("clientAlert");
  if (alertDiv) {
    alertDiv.classList.add("d-none");
  }
}

function updateUIState() {
  const unsavedChangesAlert = document.getElementById("unsavedChangesAlert");
  const unsavedCount = document.getElementById("unsavedCount");
  const btnUndo = document.getElementById("btnUndo");
  const btnSave = document.getElementById("btnSave");

  if (unsavedCount) unsavedCount.innerText = pendingActions.length;

  if (pendingActions.length > 0) {
    if (unsavedChangesAlert) unsavedChangesAlert.classList.remove("d-none");
    if (btnUndo) btnUndo.disabled = false;
    if (btnSave) btnSave.disabled = false;
  } else {
    if (unsavedChangesAlert) unsavedChangesAlert.classList.add("d-none");
    if (btnUndo) btnUndo.disabled = true;
    if (btnSave) btnSave.disabled = true;
  }
}

function handleLocalAdd() {
  const studentId = document.getElementById("studentId").value.trim();
  const lastName = document.getElementById("lastName").value.trim();
  const firstName = document.getElementById("firstName").value.trim();
  const birthDate = document.getElementById("birthDate").value.trim();
  const address = document.getElementById("address").value.trim();
  const email = document.getElementById("email").value.trim();
  const classId = document.getElementById("classId").value.trim();

  // Client-side validations
  if (!studentId) {
    showClientError("Mã sinh viên không được để trống");
    return;
  }
  if (!lastName) {
    showClientError("Họ không được để trống");
    return;
  }
  if (!firstName) {
    showClientError("Tên không được để trống");
    return;
  }
  if (!birthDate) {
    showClientError("Ngày sinh không được để trống");
    return;
  }
  if (!address) {
    showClientError("Địa chỉ không được để trống");
    return;
  }
  if (!classId) {
    showClientError("Vui lòng chọn lớp");
    return;
  }

  // Check duplicate ID in active rows
  const existingRow = findRowById(studentId);
  if (existingRow && existingRow.style.display !== "none") {
    showClientError("Mã sinh viên đã tồn tại");
    return;
  }

  hideClientError();

  // Stage ADD action
  pendingActions.push({
    type: "ADD",
    studentId: studentId,
    lastName: lastName,
    firstName: firstName,
    birthDate: birthDate,
    address: address,
    email: email,
    classId: classId
  });

  // Update DOM: Insert new row at the top
  const tableBody = document.querySelector(".table-responsive table tbody");
  if (tableBody) {
    const tr = document.createElement("tr");
    tr.classList.add("table-success");
    tr.setAttribute("data-local-added", "true");
    tr.innerHTML = `
      <td>${studentId}</td>
      <td>${lastName}</td>
      <td>${firstName}</td>
      <td>${birthDate}</td>
      <td>${address}</td>
      <td>${email}</td>
      <td>${classId}</td>
      <td class="text-end">
        <button type="button" class="btn btn-sm btn-outline-secondary btn-edit">
          <i class="bi bi-pencil"></i>
        </button>
      </td>
    `;
    tableBody.insertBefore(tr, tableBody.firstChild);
  }

  resetForm();
  updateUIState();
}

function handleLocalUpdate() {
  const studentId = document.getElementById("studentId").value.trim();
  const lastName = document.getElementById("lastName").value.trim();
  const firstName = document.getElementById("firstName").value.trim();
  const birthDate = document.getElementById("birthDate").value.trim();
  const address = document.getElementById("address").value.trim();
  const email = document.getElementById("email").value.trim();
  const classId = document.getElementById("classId").value.trim();

  // Client-side validations
  if (!lastName) {
    showClientError("Họ không được để trống");
    return;
  }
  if (!firstName) {
    showClientError("Tên không được để trống");
    return;
  }
  if (!birthDate) {
    showClientError("Ngày sinh không được để trống");
    return;
  }
  if (!address) {
    showClientError("Địa chỉ không được để trống");
    return;
  }
  if (!classId) {
    showClientError("Vui lòng chọn lớp");
    return;
  }

  const row = findRowById(studentId);
  if (!row) {
    showClientError("Không tìm thấy dòng sinh viên tương ứng");
    return;
  }

  hideClientError();

  const cells = row.querySelectorAll("td");
  const oldState = {
    lastName: cells[1].innerText.trim(),
    firstName: cells[2].innerText.trim(),
    birthDate: cells[3].innerText.trim(),
    address: cells[4].innerText.trim(),
    email: cells[5].innerText.trim(),
    classId: cells[6].innerText.trim()
  };

  if (lastName === oldState.lastName && firstName === oldState.firstName &&
      birthDate === oldState.birthDate && address === oldState.address &&
      email === oldState.email && classId === oldState.classId) {
    resetForm();
    return; // No change
  }

  // Stage UPDATE action
  pendingActions.push({
    type: "UPDATE",
    studentId: studentId,
    lastName: lastName,
    firstName: firstName,
    birthDate: birthDate,
    address: address,
    email: email,
    classId: classId,
    oldState: oldState
  });

  // Update DOM
  cells[1].innerText = lastName;
  cells[2].innerText = firstName;
  cells[3].innerText = birthDate;
  cells[4].innerText = address;
  cells[5].innerText = email;
  cells[6].innerText = classId;

  if (!row.hasAttribute("data-local-added")) {
    row.classList.add("table-warning");
  }

  resetForm();
  updateUIState();
}

function handleLocalDelete() {
  const studentId = document.getElementById("studentId").value.trim();
  const row = findRowById(studentId);
  if (!row) return;

  const cells = row.querySelectorAll("td");
  const lastName = cells[1].innerText.trim();
  const firstName = cells[2].innerText.trim();

  if (!confirm("Bạn có chắc chắn muốn xóa sinh viên " + lastName + " " + firstName + "?")) {
    return;
  }

  // Stage DELETE action
  pendingActions.push({
    type: "DELETE",
    studentId: studentId,
    lastName: lastName,
    firstName: firstName
  });

  // Update DOM
  row.style.display = "none";

  resetForm();
  updateUIState();
}

function handleUndo() {
  if (pendingActions.length === 0) return;

  const action = pendingActions.pop();
  const row = findRowById(action.studentId);

  if (action.type === "ADD") {
    if (row && row.hasAttribute("data-local-added")) {
      row.remove();
    }
  } else if (action.type === "UPDATE") {
    if (row) {
      const cells = row.querySelectorAll("td");
      cells[1].innerText = action.oldState.lastName;
      cells[2].innerText = action.oldState.firstName;
      cells[3].innerText = action.oldState.birthDate;
      cells[4].innerText = action.oldState.address;
      cells[5].innerText = action.oldState.email;
      cells[6].innerText = action.oldState.classId;

      let hasOtherUpdates = false;
      for (let act of pendingActions) {
        if (act.studentId === action.studentId && act.type === "UPDATE") {
          hasOtherUpdates = true;
          break;
        }
      }
      if (!hasOtherUpdates && !row.hasAttribute("data-local-added")) {
        row.classList.remove("table-warning");
      }
    }
  } else if (action.type === "DELETE") {
    if (row) {
      row.style.display = "";
    }
  }

  updateUIState();
  hideClientError();
}

function handleSave() {
  if (pendingActions.length === 0) return;

  // Serialize format: type:::studentId:::lastName:::firstName:::birthDate:::address:::email:::classId\n
  let dataStr = "";
  pendingActions.forEach(function (act) {
    dataStr += act.type + ":::" + act.studentId + ":::" + act.lastName + ":::" + act.firstName + ":::" + act.birthDate + ":::" + act.address + ":::" + act.email + ":::" + act.classId + "\n";
  });

  const actionsDataInput = document.getElementById("actionsDataInput");
  const saveForm = document.getElementById("saveForm");

  if (actionsDataInput && saveForm) {
    pendingActions = []; // Clear pending actions
    actionsDataInput.value = dataStr;
    saveForm.submit();
  }
}
