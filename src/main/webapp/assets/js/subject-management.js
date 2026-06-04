document.addEventListener("DOMContentLoaded", function () {
  const btnAdd = document.getElementById("btnAdd");
  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  const btnUndo = document.getElementById("btnUndo");
  const btnSave = document.getElementById("btnSave");
  const btnReset = document.getElementById("btnReset");
  const confirmDeleteButton = document.getElementById("confirmDeleteButton");
  const tableBody = document.querySelector(".management-table tbody");

  // Add click listeners to form buttons
  if (btnAdd) btnAdd.addEventListener("click", handleLocalAdd);
  if (btnUpdate) btnUpdate.addEventListener("click", handleLocalUpdate);
  if (btnDelete) btnDelete.addEventListener("click", function () {
    const subjectId = document.getElementById("subjectId").value.trim();
    const row = findRowById(subjectId);
    if (row) {
      openDeleteModal(row);
    }
  });
  if (btnUndo) btnUndo.addEventListener("click", handleUndo);
  if (btnSave) btnSave.addEventListener("click", handleSave);
  if (btnReset) btnReset.addEventListener("click", resetForm);

  if (confirmDeleteButton) {
    confirmDeleteButton.addEventListener("click", handleLocalDelete);
  }

  // Use event delegation on table body for Edit & Delete buttons
  if (tableBody) {
    tableBody.addEventListener("click", function (e) {
      const editBtn = e.target.closest(".btn-edit");
      if (editBtn) {
        const row = editBtn.closest("tr");
        fillFormFromRow(row);
        enableActionButtons();
        hideClientError();
      }

      const deleteBtn = e.target.closest(".btn-delete");
      if (deleteBtn) {
        const row = deleteBtn.closest("tr");
        openDeleteModal(row);
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
let deleteModal = null;
let rowToDelete = null;

function findRowById(subjectId) {
  const rows = document.querySelectorAll(".management-table tbody tr");
  for (let row of rows) {
    const cells = row.querySelectorAll("td");
    if (cells.length > 0 && cells[0].innerText.trim() === subjectId) {
      return row;
    }
  }
  return null;
}

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

function enableActionButtons() {
  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  if (btnUpdate) btnUpdate.disabled = false;
  if (btnDelete) btnDelete.disabled = false;
}

function resetForm() {
  const form = document.getElementById("subjectForm");
  if (form) form.reset();

  const subjectIdInput = document.getElementById("subjectId");
  if (subjectIdInput) {
    subjectIdInput.readOnly = false;
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

function openDeleteModal(row) {
  rowToDelete = row;
  const cells = row.querySelectorAll("td");
  const subjectName = cells[1].innerText.trim();
  document.getElementById("subjectDeleteModalBody").innerText =
    `Bạn có chắc muốn xóa môn học "${subjectName}" không?`;

  if (!deleteModal) {
    deleteModal = new bootstrap.Modal(
      document.getElementById("subjectDeleteModal")
    );
  }
  deleteModal.show();
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
  const subjectIdInput = document.getElementById("subjectId");
  const subjectNameInput = document.getElementById("subjectName");

  const subjectId = subjectIdInput.value.trim();
  const subjectName = subjectNameInput.value.trim();

  // Client-side Validations
  if (!subjectId) {
    showClientError("Mã môn học không được để trống");
    return;
  }
  if (subjectId.length > 5) {
    showClientError("Mã môn học tối đa 5 ký tự");
    return;
  }
  if (!/^[A-Z0-9]+$/.test(subjectId)) {
    showClientError("Mã môn học chỉ được chứa chữ in hoa và số");
    return;
  }
  if (!subjectName) {
    showClientError("Tên môn học không được để trống");
    return;
  }
  if (subjectName.length > 50) {
    showClientError("Tên môn học chỉ được chứa tối đa 50 ký tự");
    return;
  }

  // Check duplicate ID in active rows
  const existingRow = findRowById(subjectId);
  if (existingRow && existingRow.style.display !== "none") {
    showClientError("Mã môn học đã tồn tại");
    return;
  }

  hideClientError();

  // Stage ADD action
  pendingActions.push({
    type: "ADD",
    subjectId: subjectId,
    subjectName: subjectName
  });

  // Update DOM: Insert new row at the top
  const tableBody = document.querySelector(".management-table tbody");
  if (tableBody) {
    const tr = document.createElement("tr");
    tr.classList.add("table-success");
    tr.setAttribute("data-local-added", "true");
    tr.innerHTML = `
      <td class="px-4 fw-medium">${subjectId}</td>
      <td>${subjectName}</td>
      <td class="text-end pe-4">
        <button type="button" class="btn btn-sm btn-outline-secondary me-2 btn-edit">
          <i class="bi bi-pencil p-2"></i> Chỉnh sửa
        </button>
        <button type="button" class="btn btn-sm btn-outline-danger btn-delete">
          <i class="bi bi-trash p-2"></i> Xóa
        </button>
      </td>
    `;
    tableBody.insertBefore(tr, tableBody.firstChild);
  }

  resetForm();
  updateUIState();
}

function handleLocalUpdate() {
  const subjectId = document.getElementById("subjectId").value.trim();
  const subjectName = document.getElementById("subjectName").value.trim();

  if (!subjectName) {
    showClientError("Tên môn học không được để trống");
    return;
  }
  if (subjectName.length > 50) {
    showClientError("Tên môn học chỉ được chứa tối đa 50 ký tự");
    return;
  }

  const row = findRowById(subjectId);
  if (!row) {
    showClientError("Không tìm thấy dòng môn học tương ứng");
    return;
  }

  hideClientError();

  const cells = row.querySelectorAll("td");
  const oldSubjectName = cells[1].innerText.trim();

  if (subjectName === oldSubjectName) {
    resetForm();
    return; // No change
  }

  // Stage UPDATE action
  pendingActions.push({
    type: "UPDATE",
    subjectId: subjectId,
    subjectName: subjectName,
    oldSubjectName: oldSubjectName
  });

  // Update DOM
  cells[1].innerText = subjectName;
  if (!row.hasAttribute("data-local-added")) {
    row.classList.add("table-warning");
  }

  resetForm();
  updateUIState();
}

function handleLocalDelete() {
  if (!rowToDelete) return;

  const cells = rowToDelete.querySelectorAll("td");
  const subjectId = cells[0].innerText.trim();
  const subjectName = cells[1].innerText.trim();

  // Stage DELETE action
  pendingActions.push({
    type: "DELETE",
    subjectId: subjectId,
    subjectName: subjectName
  });

  // Update DOM
  rowToDelete.style.display = "none";

  if (deleteModal) {
    deleteModal.hide();
  }

  rowToDelete = null;
  resetForm();
  updateUIState();
}

function handleUndo() {
  if (pendingActions.length === 0) return;

  const action = pendingActions.pop();
  const row = findRowById(action.subjectId);

  if (action.type === "ADD") {
    if (row && row.hasAttribute("data-local-added")) {
      row.remove();
    }
  } else if (action.type === "UPDATE") {
    if (row) {
      const cells = row.querySelectorAll("td");
      cells[1].innerText = action.oldSubjectName;

      // Only remove the highlight if this was the last pending update for this row
      let hasOtherUpdates = false;
      for (let act of pendingActions) {
        if (act.subjectId === action.subjectId && act.type === "UPDATE") {
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

  // Serialize to simple text format: type:::subjectId:::subjectName\n
  let dataStr = "";
  pendingActions.forEach(function (act) {
    dataStr += act.type + ":::" + act.subjectId + ":::" + act.subjectName + "\n";
  });

  const actionsDataInput = document.getElementById("actionsDataInput");
  const saveForm = document.getElementById("saveForm");

  if (actionsDataInput && saveForm) {
    // Clear pending actions so beforeunload doesn't prompt
    const actionsCopy = [...pendingActions];
    pendingActions = [];

    actionsDataInput.value = dataStr;
    saveForm.submit();
  }
}
