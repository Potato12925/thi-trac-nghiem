document.addEventListener("DOMContentLoaded", function () {
  const btnAdd = document.getElementById("btnAdd");
  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  const btnUndo = document.getElementById("btnUndo");
  const btnSave = document.getElementById("btnSave");
  const btnReset = document.getElementById("btnReset");
  const tableBody = document.querySelector(".table-responsive table tbody");
  const imageFileInput = document.getElementById("imageFileInput");
  const btnRemoveImage = document.getElementById("btnRemoveImage");
  const contentInput = document.getElementById("content");
  const subjectSelect = document.getElementById("subjectId");

  // Add click listeners to form buttons
  if (btnAdd) btnAdd.addEventListener("click", handleLocalAdd);
  if (btnUpdate) btnUpdate.addEventListener("click", handleLocalUpdate);
  if (btnDelete) btnDelete.addEventListener("click", handleLocalDelete);
  if (btnUndo) btnUndo.addEventListener("click", handleUndo);
  if (btnSave) btnSave.addEventListener("click", handleSave);
  if (btnReset) btnReset.addEventListener("click", resetForm);

  if (imageFileInput) imageFileInput.addEventListener("change", handleImageUpload);
  if (btnRemoveImage) btnRemoveImage.addEventListener("click", handleImageRemove);

  if (contentInput) {
    contentInput.addEventListener("input", function() {
      updateLatexPreview();
      scheduleDuplicateCheck();
    });
    contentInput.addEventListener("change", updateLatexPreview);
  }
  if (subjectSelect) {
    subjectSelect.addEventListener("change", scheduleDuplicateCheck);
    subjectSelect.addEventListener("input", scheduleDuplicateCheck);
  }

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

  // Render LaTeX on page load safely if script has finished loading
  if (window.renderMathInElement) {
    renderMathInElement(document.body, {
      delimiters: [
        {left: '$$', right: '$$', display: true},
        {left: '$', right: '$', display: false}
      ]
    });
  }
});

let pendingActions = [];
let activeRow = null;
let tempIdCounter = 1;
let duplicateCheckTimeout;

function updateLatexPreview() {
  const content = document.getElementById("content").value;
  const preview = document.getElementById("latexPreview");
  if (preview) {
    preview.innerHTML = content.replace(/\n/g, "<br>");
    if (window.renderMathInElement) {
      renderMathInElement(preview, {
        delimiters: [
          {left: '$$', right: '$$', display: true},
          {left: '$', right: '$', display: false}
        ]
      });
    }
  }
}

function scheduleDuplicateCheck() {
  clearTimeout(duplicateCheckTimeout);
  duplicateCheckTimeout = setTimeout(checkDuplicateQuestions, 500);
}

function checkDuplicateQuestions() {
  const content = document.getElementById("content").value.trim();
  const subjectId = document.getElementById("subjectId").value.trim();
  const questionId = document.getElementById("questionId").value.trim();
  const duplicateAlert = document.getElementById("duplicateAlert");
  const duplicateList = document.getElementById("duplicateList");
  
  if (!content || !subjectId || content.length < 5) {
    if (duplicateAlert) duplicateAlert.classList.add("d-none");
    return;
  }
  
  let url = `${window.APP_BASE_PATH}/questions/check-duplicate?content=${encodeURIComponent(content)}&subjectId=${encodeURIComponent(subjectId)}`;
  if (questionId && !questionId.startsWith("T")) {
    url += `&excludeId=${questionId}`;
  }
  
  fetch(url)
    .then(res => res.json())
    .then(data => {
      if (data && data.length > 0) {
        if (duplicateList) {
          duplicateList.innerHTML = "";
          data.forEach(item => {
            const li = document.createElement("li");
            li.innerHTML = `Câu hỏi ID: <strong>${item.questionId}</strong> (Độ trùng lặp: <strong>${item.similarity}%</strong>) - <em>"${item.content}"</em>`;
            duplicateList.appendChild(li);
          });
        }
        if (duplicateAlert) duplicateAlert.classList.remove("d-none");
      } else {
        if (duplicateAlert) duplicateAlert.classList.add("d-none");
      }
    })
    .catch(err => {
      console.error("Lỗi kiểm tra trùng lặp:", err);
    });
}

function handleImageUpload(e) {
  const file = e.target.files[0];
  if (!file) return;

  const formData = new FormData();
  formData.append("file", file);

  const url = `${window.APP_BASE_PATH}/questions/upload-image`;
  fetch(url, {
    method: "POST",
    body: formData
  })
  .then(res => res.json())
  .then(data => {
    if (data.success) {
      document.getElementById("imageUrlInput").value = data.imageUrl;
      const previewImg = document.getElementById("imagePreview");
      previewImg.src = window.APP_BASE_PATH + data.imageUrl;
      document.getElementById("imagePreviewContainer").classList.remove("d-none");
    } else {
      alert("Tải ảnh thất bại: " + data.message);
    }
  })
  .catch(err => {
    console.error("Lỗi upload ảnh:", err);
    alert("Lỗi tải ảnh lên server.");
  });
}

function handleImageRemove() {
  document.getElementById("imageUrlInput").value = "";
  document.getElementById("imageFileInput").value = "";
  document.getElementById("imagePreviewContainer").classList.add("d-none");
  document.getElementById("imagePreview").src = "";
}

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
  
  // Cells[4] is the image column
  const img = cells[4].querySelector("img");
  const imageUrl = img ? img.getAttribute("src").replace(window.APP_BASE_PATH, "") : "";
  document.getElementById("imageUrlInput").value = imageUrl;
  const previewContainer = document.getElementById("imagePreviewContainer");
  const previewImg = document.getElementById("imagePreview");
  if (imageUrl) {
    previewImg.src = window.APP_BASE_PATH + imageUrl;
    previewContainer.classList.remove("d-none");
  } else {
    previewImg.src = "";
    previewContainer.classList.add("d-none");
  }

  document.getElementById("optionA").value = cells[5].innerText.trim();
  document.getElementById("optionB").value = cells[6].innerText.trim();
  document.getElementById("optionC").value = cells[7].innerText.trim();
  document.getElementById("optionD").value = cells[8].innerText.trim();
  document.getElementById("correctAnswer").value = cells[9].innerText.trim();
  document.getElementById("lecturerId").value = cells[10].innerText.trim();
  document.getElementById("questionId").readOnly = true;
  
  setActiveRow(row);
  updateLatexPreview();
  scheduleDuplicateCheck();
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
    questionIdInput.readOnly = true;
  }

  const loggedLecturerId = window.LOGGED_LECTURER_ID || "";
  if (loggedLecturerId && document.getElementById("lecturerId")) {
    document.getElementById("lecturerId").value = loggedLecturerId;
  }

  handleImageRemove();

  const duplicateAlert = document.getElementById("duplicateAlert");
  if (duplicateAlert) duplicateAlert.classList.add("d-none");
  const latexPreview = document.getElementById("latexPreview");
  if (latexPreview) latexPreview.innerHTML = "";

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

// Helper to render KaTeX dynamically in an element
function renderMathInEl(el) {
  if (window.renderMathInElement) {
    renderMathInElement(el, {
      delimiters: [
        {left: '$$', right: '$$', display: true},
        {left: '$', right: '$', display: false}
      ]
    });
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
  const imageUrl = document.getElementById("imageUrlInput").value.trim();
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
    imageUrl: imageUrl,
    optionA: optionA,
    optionB: optionB,
    optionC: optionC,
    optionD: optionD,
    correctAnswer: correctAnswer,
    lecturerId: lecturerId
  });

  // Update DOM
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
      <td>${imageUrl ? `<img src="${window.APP_BASE_PATH}${imageUrl}" class="img-thumbnail" style="max-height: 40px;"/>` : ''}</td>
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
    renderMathInEl(tr);
  }

  resetForm();
  updateUIState();
}

function handleLocalUpdate() {
  const questionId = document.getElementById("questionId").value.trim();
  const subjectId = document.getElementById("subjectId").value.trim();
  const level = document.getElementById("level").value.trim();
  const content = document.getElementById("content").value.trim();
  const imageUrl = document.getElementById("imageUrlInput").value.trim();
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
  const oldImg = cells[4].querySelector("img");
  const oldImageUrl = oldImg ? oldImg.getAttribute("src").replace(window.APP_BASE_PATH, "") : "";

  const oldState = {
    subjectId: cells[1].innerText.trim(),
    level: cells[2].innerText.trim(),
    content: cells[3].innerText.trim(),
    imageUrl: oldImageUrl,
    optionA: cells[5].innerText.trim(),
    optionB: cells[6].innerText.trim(),
    optionC: cells[7].innerText.trim(),
    optionD: cells[8].innerText.trim(),
    correctAnswer: cells[9].innerText.trim(),
    lecturerId: cells[10].innerText.trim()
  };

  if (subjectId === oldState.subjectId && level === oldState.level &&
      content === oldState.content && imageUrl === oldState.imageUrl &&
      optionA === oldState.optionA && optionB === oldState.optionB &&
      optionC === oldState.optionC && optionD === oldState.optionD &&
      correctAnswer === oldState.correctAnswer && lecturerId === oldState.lecturerId) {
    resetForm();
    return;
  }

  // Stage UPDATE action
  pendingActions.push({
    type: "UPDATE",
    questionId: questionId,
    subjectId: subjectId,
    level: level,
    content: content,
    imageUrl: imageUrl,
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
  cells[4].innerHTML = imageUrl ? `<img src="${window.APP_BASE_PATH}${imageUrl}" class="img-thumbnail" style="max-height: 40px;"/>` : '';
  cells[5].innerText = optionA;
  cells[6].innerText = optionB;
  cells[7].innerText = optionC;
  cells[8].innerText = optionD;
  cells[9].innerText = correctAnswer;
  cells[10].innerText = lecturerId;

  if (!row.hasAttribute("data-local-added")) {
    row.classList.add("table-warning");
  }

  renderMathInEl(row);

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
  const img = cells[4].querySelector("img");
  const imageUrl = img ? img.getAttribute("src").replace(window.APP_BASE_PATH, "") : "";

  // Stage DELETE action
  pendingActions.push({
    type: "DELETE",
    questionId: questionId,
    subjectId: cells[1].innerText.trim(),
    level: cells[2].innerText.trim(),
    content: cells[3].innerText.trim(),
    imageUrl: imageUrl,
    optionA: cells[5].innerText.trim(),
    optionB: cells[6].innerText.trim(),
    optionC: cells[7].innerText.trim(),
    optionD: cells[8].innerText.trim(),
    correctAnswer: cells[9].innerText.trim(),
    lecturerId: cells[10].innerText.trim()
  });

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
      cells[4].innerHTML = action.oldState.imageUrl ? `<img src="${window.APP_BASE_PATH}${action.oldState.imageUrl}" class="img-thumbnail" style="max-height: 40px;"/>` : '';
      cells[5].innerText = action.oldState.optionA;
      cells[6].innerText = action.oldState.optionB;
      cells[7].innerText = action.oldState.optionC;
      cells[8].innerText = action.oldState.optionD;
      cells[9].innerText = action.oldState.correctAnswer;
      cells[10].innerText = action.oldState.lecturerId;

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
      renderMathInEl(row);
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

  // Serialize format: type:::questionId:::subjectId:::level:::content:::optionA:::optionB:::optionC:::optionD:::correctAnswer:::lecturerId:::imageUrl\n
  let dataStr = "";
  pendingActions.forEach(function (act) {
    dataStr += act.type + ":::" + act.questionId + ":::" + act.subjectId + ":::" + act.level + ":::" + act.content + ":::" + act.optionA + ":::" + act.optionB + ":::" + act.optionC + ":::" + act.optionD + ":::" + act.correctAnswer + ":::" + act.lecturerId + ":::" + (act.imageUrl || "") + "\n";
  });

  const actionsDataInput = document.getElementById("actionsDataInput");
  const saveForm = document.getElementById("saveForm");

  if (actionsDataInput && saveForm) {
    pendingActions = [];
    actionsDataInput.value = dataStr;
    saveForm.submit();
  }
}
