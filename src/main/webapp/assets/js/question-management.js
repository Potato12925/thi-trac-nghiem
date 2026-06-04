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
      const editBtn = e.target.closest(".btn-edit-question");
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
let tempIdCounter = 1;

function findRowById(questionId) {
  const rows = document.querySelectorAll(".table-responsive table tbody tr");
  for (let row of rows) {
    const cells = row.querySelectorAll("td");
    if (cells.length > 0 && cells[0].innerText.trim() === questionId) {
      return row;
    }
  }
  return null;
}

function fillFormFromRow(row) {
  const cells = row.querySelectorAll("td");
  document.getElementById("questionId").value = cells[0].innerText.trim();
  document.getElementById("subjectId").value = cells[1].innerText.trim();
  document.getElementById("level").value = cells[2].innerText.trim();
  document.getElementById("content").value = cells[3].innerText.trim();
  document.getElementById("optionA").value = cells[4].innerText.trim();
  document.getElementById("optionB").value = cells[5].innerText.trim();
  document.getElementById("optionC").value = cells[6].innerText.trim();
  document.getElementById("optionD").value = cells[7].innerText.trim();
  document.getElementById("correctAnswer").value = cells[8].innerText.trim();
  document.getElementById("lecturerId").value = cells[9].innerText.trim();
  document.getElementById("questionId").readOnly = true;
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
  const form = document.getElementById("questionForm");
  if (form) form.reset();

  const questionIdInput = document.getElementById("questionId");
  if (questionIdInput) {
    questionIdInput.value = "";
    questionIdInput.readOnly = true; // Question ID is generated, keep readonly by default
  }

  // If loggedLecturerId is present, restore it in form
  const loggedLecturerId = window.LOGGED_LECTURER_ID || "";
  if (loggedLecturerId && document.getElementById("lecturerId")) {
    document.getElementById("lecturerId").value = loggedLecturerId;
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
  const subjectId = document.getElementById("subjectId").value.trim();
  const level = document.getElementById("level").value.trim();
  const content = document.getElementById("content").value.trim();
  const optionA = document.getElementById("optionA").value.trim();
  const optionB = document.getElementById("optionB").value.trim();
  const optionC = document.getElementById("optionC").value.trim();
  const optionD = document.getElementById("optionD").value.trim();
  const correctAnswer = document.getElementById("correctAnswer").value.trim();
  const lecturerId = document.getElementById("lecturerId").value.trim();

  // Client-side validations
  if (!subjectId) {
    showClientError("Mã môn học không được để trống");
    return;
  }
  if (!level) {
    showClientError("Vui lòng chọn trình độ");
    return;
  }
  if (!content) {
    showClientError("Nội dung câu hỏi không được để trống");
    return;
  }
  if (content.length > 200) {
    showClientError("Nội dung câu hỏi không được vượt quá 200 ký tự");
    return;
  }
  if (!optionA || !optionB || !optionC || !optionD) {
    showClientError("Vui lòng nhập đầy đủ các đáp án lựa chọn A, B, C, D");
    return;
  }
  if (!correctAnswer) {
    showClientError("Vui lòng chọn đáp án đúng");
    return;
  }
  if (!lecturerId) {
    showClientError("Mã giảng viên không được để trống");
    return;
  }

  hideClientError();

  const tempId = "T" + tempIdCounter++;

  // Stage ADD action
  pendingActions.push({
    type: "ADD",
    questionId: tempId,
    subjectId: subjectId,
    level: level,
    content: content,
    optionA: optionA,
    optionB: optionB,
    optionC: optionC,
    optionD: optionD,
    correctAnswer: correctAnswer,
    lecturerId: lecturerId
  });

  // Update DOM: Insert new row at the top
  const tableBody = document.querySelector(".table-responsive table tbody");
  if (tableBody) {
    const tr = document.createElement("tr");
    tr.classList.add("table-success");
    tr.setAttribute("data-local-added", "true");
    tr.innerHTML = `
      <td>${tempId}</td>
      <td>${subjectId}</td>
      <td>${level}</td>
      <td>${content}</td>
      <td>${optionA}</td>
      <td>${optionB}</td>
      <td>${optionC}</td>
      <td>${optionD}</td>
      <td>${correctAnswer}</td>
      <td>${lecturerId}</td>
      <td class="text-end pe-4">
        <button type="button" class="btn btn-sm btn-outline-secondary btn-edit-question">
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
  const questionId = document.getElementById("questionId").value.trim();
  const subjectId = document.getElementById("subjectId").value.trim();
  const level = document.getElementById("level").value.trim();
  const content = document.getElementById("content").value.trim();
  const optionA = document.getElementById("optionA").value.trim();
  const optionB = document.getElementById("optionB").value.trim();
  const optionC = document.getElementById("optionC").value.trim();
  const optionD = document.getElementById("optionD").value.trim();
  const correctAnswer = document.getElementById("correctAnswer").value.trim();
  const lecturerId = document.getElementById("lecturerId").value.trim();

  // Client-side validations
  if (!subjectId) {
    showClientError("Mã môn học không được để trống");
    return;
  }
  if (!level) {
    showClientError("Vui lòng chọn trình độ");
    return;
  }
  if (!content) {
    showClientError("Nội dung câu hỏi không được để trống");
    return;
  }
  if (content.length > 200) {
    showClientError("Nội dung câu hỏi không được vượt quá 200 ký tự");
    return;
  }
  if (!optionA || !optionB || !optionC || !optionD) {
    showClientError("Vui lòng nhập đầy đủ các đáp án lựa chọn A, B, C, D");
    return;
  }
  if (!correctAnswer) {
    showClientError("Vui lòng chọn đáp án đúng");
    return;
  }
  if (!lecturerId) {
    showClientError("Mã giảng viên không được để trống");
    return;
  }

  const row = findRowById(questionId);
  if (!row) {
    showClientError("Không tìm thấy dòng câu hỏi tương ứng");
    return;
  }

  hideClientError();

  const cells = row.querySelectorAll("td");
  const oldState = {
    subjectId: cells[1].innerText.trim(),
    level: cells[2].innerText.trim(),
    content: cells[3].innerText.trim(),
    optionA: cells[4].innerText.trim(),
    optionB: cells[5].innerText.trim(),
    optionC: cells[6].innerText.trim(),
    optionD: cells[7].innerText.trim(),
    correctAnswer: cells[8].innerText.trim(),
    lecturerId: cells[9].innerText.trim()
  };

  if (subjectId === oldState.subjectId && level === oldState.level &&
      content === oldState.content && optionA === oldState.optionA &&
      optionB === oldState.optionB && optionC === oldState.optionC &&
      optionD === oldState.optionD && correctAnswer === oldState.correctAnswer &&
      lecturerId === oldState.lecturerId) {
    resetForm();
    return; // No change
  }

  // Stage UPDATE action
  pendingActions.push({
    type: "UPDATE",
    questionId: questionId,
    subjectId: subjectId,
    level: level,
    content: content,
    optionA: optionA,
    optionB: optionB,
    optionC: optionC,
    optionD: optionD,
    correctAnswer: correctAnswer,
    lecturerId: lecturerId,
    oldState: oldState
  });

  // Update DOM
  cells[1].innerText = subjectId;
  cells[2].innerText = level;
  cells[3].innerText = content;
  cells[4].innerText = optionA;
  cells[5].innerText = optionB;
  cells[6].innerText = optionC;
  cells[7].innerText = optionD;
  cells[8].innerText = correctAnswer;
  cells[9].innerText = lecturerId;

  if (!row.hasAttribute("data-local-added")) {
    row.classList.add("table-warning");
  }

  resetForm();
  updateUIState();
}

function handleLocalDelete() {
  const questionId = document.getElementById("questionId").value.trim();
  const row = findRowById(questionId);
  if (!row) return;

  if (!confirm("Bạn có chắc chắn muốn xóa câu hỏi mã " + questionId + "?")) {
    return;
  }

  const cells = row.querySelectorAll("td");

  // Stage DELETE action
  pendingActions.push({
    type: "DELETE",
    questionId: questionId,
    subjectId: cells[1].innerText.trim(),
    level: cells[2].innerText.trim(),
    content: cells[3].innerText.trim(),
    optionA: cells[4].innerText.trim(),
    optionB: cells[5].innerText.trim(),
    optionC: cells[6].innerText.trim(),
    optionD: cells[7].innerText.trim(),
    correctAnswer: cells[8].innerText.trim(),
    lecturerId: cells[9].innerText.trim()
  });

  // Update DOM
  row.style.display = "none";

  resetForm();
  updateUIState();
}

function handleUndo() {
  if (pendingActions.length === 0) return;

  const action = pendingActions.pop();
  const row = findRowById(action.questionId);

  if (action.type === "ADD") {
    if (row && row.hasAttribute("data-local-added")) {
      row.remove();
    }
  } else if (action.type === "UPDATE") {
    if (row) {
      const cells = row.querySelectorAll("td");
      cells[1].innerText = action.oldState.subjectId;
      cells[2].innerText = action.oldState.level;
      cells[3].innerText = action.oldState.content;
      cells[4].innerText = action.oldState.optionA;
      cells[5].innerText = action.oldState.optionB;
      cells[6].innerText = action.oldState.optionC;
      cells[7].innerText = action.oldState.optionD;
      cells[8].innerText = action.oldState.correctAnswer;
      cells[9].innerText = action.oldState.lecturerId;

      let hasOtherUpdates = false;
      for (let act of pendingActions) {
        if (act.questionId === action.questionId && act.type === "UPDATE") {
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

  // Serialize format: type:::questionId:::subjectId:::level:::content:::optionA:::optionB:::optionC:::optionD:::correctAnswer:::lecturerId\n
  let dataStr = "";
  pendingActions.forEach(function (act) {
    dataStr += act.type + ":::" + act.questionId + ":::" + act.subjectId + ":::" + act.level + ":::" + act.content + ":::" + act.optionA + ":::" + act.optionB + ":::" + act.optionC + ":::" + act.optionD + ":::" + act.correctAnswer + ":::" + act.lecturerId + "\n";
  });

  const actionsDataInput = document.getElementById("actionsDataInput");
  const saveForm = document.getElementById("saveForm");

  if (actionsDataInput && saveForm) {
    pendingActions = []; // Clear pending actions
    actionsDataInput.value = dataStr;
    saveForm.submit();
  }
}
