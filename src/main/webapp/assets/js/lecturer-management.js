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

function findRowById(lecturerId) {
  const rows = document.querySelectorAll(".table-responsive table tbody tr");
  for (let row of rows) {
    const id = row.getAttribute("data-id") || row.querySelectorAll("td")[0]?.innerText.trim();
    if (id === lecturerId) {
      return row;
    }
  }
  return null;
}

function fillFormFromRow(row) {
  document.getElementById("lecturerId").value = row.getAttribute("data-id") || row.querySelectorAll("td")[0]?.innerText.trim() || "";
  document.getElementById("lastName").value = row.getAttribute("data-lastname") || row.querySelectorAll("td")[1]?.innerText.trim() || "";
  document.getElementById("firstName").value = row.getAttribute("data-firstname") || row.querySelectorAll("td")[2]?.innerText.trim() || "";
  document.getElementById("phoneNumber").value = row.getAttribute("data-phone") || row.querySelectorAll("td")[3]?.innerText.trim() || "";
  document.getElementById("address").value = row.getAttribute("data-address") || row.querySelectorAll("td")[4]?.innerText.trim() || "";
  document.getElementById("email").value = row.getAttribute("data-email") || row.querySelectorAll("td")[5]?.innerText.trim() || "";
  document.getElementById("lecturerId").readOnly = true;
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
  const form = document.getElementById("lecturerForm");
  if (form) form.reset();

  const lecturerIdInput = document.getElementById("lecturerId");
  if (lecturerIdInput) {
    lecturerIdInput.readOnly = false;
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
  const lecturerId = document.getElementById("lecturerId").value.trim();
  const lastName = document.getElementById("lastName").value.trim();
  const firstName = document.getElementById("firstName").value.trim();
  const phoneNumber = document.getElementById("phoneNumber").value.trim();
  const address = document.getElementById("address").value.trim();
  const email = document.getElementById("email").value.trim();

  // Client-side validations
  if (!lecturerId) {
    showClientError("Mã giảng viên không được để trống");
    return;
  }
  if (lecturerId.length > 8) {
    showClientError("Mã giảng viên tối đa 8 ký tự");
    return;
  }
  if (!/^[A-Z0-9]+$/.test(lecturerId)) {
    showClientError("Mã giảng viên chỉ được chứa chữ in hoa và số");
    return;
  }
  if (!lastName) {
    showClientError("Họ không được để trống");
    return;
  }
  if (lastName.length > 40) {
    showClientError("Họ tối đa 40 ký tự");
    return;
  }
  if (!firstName) {
    showClientError("Tên không được để trống");
    return;
  }
  if (firstName.length > 10) {
    showClientError("Tên tối đa 10 ký tự");
    return;
  }
  if (!phoneNumber) {
    showClientError("Số điện thoại không được để trống");
    return;
  }
  if (phoneNumber.length > 15) {
    showClientError("Số điện thoại tối đa 15 ký tự");
    return;
  }
  if (!/^[0-9+\-\s]+$/.test(phoneNumber)) {
    showClientError("Số điện thoại không hợp lệ");
    return;
  }
  if (!address) {
    showClientError("Địa chỉ không được để trống");
    return;
  }
  if (address.length > 50) {
    showClientError("Địa chỉ tối đa 50 ký tự");
    return;
  }

  // Check duplicate ID in active rows
  const existingRow = findRowById(lecturerId);
  if (existingRow && existingRow.style.display !== "none") {
    showClientError("Mã giảng viên đã tồn tại");
    return;
  }

  hideClientError();

  // Stage ADD action
  pendingActions.push({
    type: "ADD",
    lecturerId: lecturerId,
    lastName: lastName,
    firstName: firstName,
    phoneNumber: phoneNumber,
    address: address,
    email: email
  });

  // Update DOM: Insert new row at the top
  const tableBody = document.querySelector(".table-responsive table tbody");
  if (tableBody) {
    const tr = document.createElement("tr");
    tr.classList.add("table-success");
    tr.setAttribute("data-local-added", "true");
    tr.setAttribute("data-id", lecturerId);
    tr.setAttribute("data-lastname", lastName);
    tr.setAttribute("data-firstname", firstName);
    tr.setAttribute("data-phone", phoneNumber);
    tr.setAttribute("data-address", address);
    tr.setAttribute("data-email", email);
    tr.innerHTML = `
      <td class="fw-medium text-success">${lecturerId}</td>
      <td>${lastName}</td>
      <td>${firstName}</td>
      <td>${phoneNumber}</td>
      <td>${address}</td>
      <td>${email}</td>
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
  const lecturerId = document.getElementById("lecturerId").value.trim();
  const lastName = document.getElementById("lastName").value.trim();
  const firstName = document.getElementById("firstName").value.trim();
  const phoneNumber = document.getElementById("phoneNumber").value.trim();
  const address = document.getElementById("address").value.trim();
  const email = document.getElementById("email").value.trim();

  // Client-side validations
  if (!lastName) {
    showClientError("Họ không được để trống");
    return;
  }
  if (lastName.length > 40) {
    showClientError("Họ tối đa 40 ký tự");
    return;
  }
  if (!firstName) {
    showClientError("Tên không được để trống");
    return;
  }
  if (firstName.length > 10) {
    showClientError("Tên tối đa 10 ký tự");
    return;
  }
  if (!phoneNumber) {
    showClientError("Số điện thoại không được để trống");
    return;
  }
  if (phoneNumber.length > 15) {
    showClientError("Số điện thoại tối đa 15 ký tự");
    return;
  }
  if (!/^[0-9+\-\s]+$/.test(phoneNumber)) {
    showClientError("Số điện thoại không hợp lệ");
    return;
  }
  if (!address) {
    showClientError("Địa chỉ không được để trống");
    return;
  }
  if (address.length > 50) {
    showClientError("Địa chỉ tối đa 50 ký tự");
    return;
  }

  const row = findRowById(lecturerId);
  if (!row) {
    showClientError("Không tìm thấy dòng giảng viên tương ứng");
    return;
  }

  hideClientError();

  const oldState = {
    lastName: row.getAttribute("data-lastname") || row.querySelectorAll("td")[1]?.innerText.trim(),
    firstName: row.getAttribute("data-firstname") || row.querySelectorAll("td")[2]?.innerText.trim(),
    phoneNumber: row.getAttribute("data-phone") || row.querySelectorAll("td")[3]?.innerText.trim(),
    address: row.getAttribute("data-address") || row.querySelectorAll("td")[4]?.innerText.trim(),
    email: row.getAttribute("data-email") || row.querySelectorAll("td")[5]?.innerText.trim()
  };

  if (lastName === oldState.lastName && firstName === oldState.firstName &&
      phoneNumber === oldState.phoneNumber && address === oldState.address &&
      email === oldState.email) {
    resetForm();
    return; // No change
  }

  // Stage UPDATE action
  pendingActions.push({
    type: "UPDATE",
    lecturerId: lecturerId,
    lastName: lastName,
    firstName: firstName,
    phoneNumber: phoneNumber,
    address: address,
    email: email,
    oldState: oldState
  });

  // Update DOM & attributes
  row.setAttribute("data-lastname", lastName);
  row.setAttribute("data-firstname", firstName);
  row.setAttribute("data-phone", phoneNumber);
  row.setAttribute("data-address", address);
  row.setAttribute("data-email", email);

  const cells = row.querySelectorAll("td");
  cells[1].innerText = lastName;
  cells[2].innerText = firstName;
  cells[3].innerText = phoneNumber;
  cells[4].innerText = address;
  cells[5].innerText = email;

  if (!row.hasAttribute("data-local-added")) {
    row.classList.add("table-warning");
  }

  resetForm();
  updateUIState();
}

function handleLocalDelete() {
  const lecturerId = document.getElementById("lecturerId").value.trim();
  const row = findRowById(lecturerId);
  if (!row) return;

  const lastName = row.getAttribute("data-lastname") || row.querySelectorAll("td")[1]?.innerText.trim();
  const firstName = row.getAttribute("data-firstname") || row.querySelectorAll("td")[2]?.innerText.trim();

  if (!confirm("Bạn có chắc chắn muốn xóa giảng viên " + lastName + " " + firstName + "?")) {
    return;
  }

  // Stage DELETE action
  pendingActions.push({
    type: "DELETE",
    lecturerId: lecturerId,
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
  const row = findRowById(action.lecturerId);

  if (action.type === "ADD") {
    if (row && row.hasAttribute("data-local-added")) {
      row.remove();
    }
  } else if (action.type === "UPDATE") {
    if (row) {
      row.setAttribute("data-lastname", action.oldState.lastName);
      row.setAttribute("data-firstname", action.oldState.firstName);
      row.setAttribute("data-phone", action.oldState.phoneNumber);
      row.setAttribute("data-address", action.oldState.address);
      row.setAttribute("data-email", action.oldState.email);

      const cells = row.querySelectorAll("td");
      cells[1].innerText = action.oldState.lastName;
      cells[2].innerText = action.oldState.firstName;
      cells[3].innerText = action.oldState.phoneNumber;
      cells[4].innerText = action.oldState.address;
      cells[5].innerText = action.oldState.email;

      let hasOtherUpdates = false;
      for (let act of pendingActions) {
        if (act.lecturerId === action.lecturerId && act.type === "UPDATE") {
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

  // Serialize format: type:::lecturerId:::lastName:::firstName:::phoneNumber:::address:::email\n
  let dataStr = "";
  pendingActions.forEach(function (act) {
    dataStr += act.type + ":::" + act.lecturerId + ":::" + act.lastName + ":::" + act.firstName + ":::" + act.phoneNumber + ":::" + act.address + ":::" + act.email + "\n";
  });

  const actionsDataInput = document.getElementById("actionsDataInput");
  const saveForm = document.getElementById("saveForm");

  if (actionsDataInput && saveForm) {
    pendingActions = []; // Clear pending actions
    actionsDataInput.value = dataStr;
    saveForm.submit();
  }
}
