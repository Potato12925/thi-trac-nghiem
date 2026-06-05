document.addEventListener("DOMContentLoaded", function () {
  const tableBody = document.querySelector(".table-responsive table tbody");
  const btnQueryAnalytics = document.getElementById("btnQueryAnalytics");
  const anSubjectSelect = document.getElementById("anSubjectSelect");
  const anTryNumberSelect = document.getElementById("anTryNumberSelect");

  let currentClassId = "";
  let currentClassName = "";
  let gradeChartInstance = null;

  // Event delegation for Analytics button
  if (tableBody) {
    tableBody.addEventListener("click", function (e) {
      const analyticsBtn = e.target.closest(".btn-analytics");
      if (analyticsBtn) {
        currentClassId = analyticsBtn.getAttribute("data-class-id").trim();
        currentClassName = analyticsBtn.getAttribute("data-class-name").trim();
        openAnalyticsModal();
      }
    });
  }

  if (btnQueryAnalytics) {
    btnQueryAnalytics.addEventListener("click", queryAnalyticsData);
  }

  function openAnalyticsModal() {
    const modalElement = document.getElementById("classroomAnalyticsModal");
    if (!modalElement) return;

    document.getElementById("anClassNameHeader").innerText = `${currentClassName} (${currentClassId})`;
    
    // Hide content and show loading initially
    document.getElementById("analyticsContent").classList.add("d-none");
    document.getElementById("analyticsError").classList.add("d-none");
    document.getElementById("analyticsLoading").classList.remove("d-none");

    const modal = new bootstrap.Modal(modalElement);
    modal.show();

    // Fetch subjects first to populate dropdown
    fetch(`${window.APP_BASE_PATH}/classrooms/${currentClassId}/analytics`)
      .then(res => res.json())
      .then(data => {
        document.getElementById("analyticsLoading").classList.add("d-none");
        if (data.error) {
          showError(data.error);
          return;
        }

        // Populate subjects
        if (anSubjectSelect) {
          anSubjectSelect.innerHTML = "";
          if (data.subjects && data.subjects.length > 0) {
            data.subjects.forEach(sub => {
              const opt = document.createElement("option");
              opt.value = sub.subjectId;
              opt.innerText = `${sub.subjectId} - ${sub.subjectName}`;
              anSubjectSelect.appendChild(opt);
            });
            // Automatically query for the first subject
            queryAnalyticsData();
          } else {
            showError("Không tìm thấy môn học nào trong hệ thống.");
          }
        }
      })
      .catch(err => {
        console.error(err);
        document.getElementById("analyticsLoading").classList.add("d-none");
        showError("Lỗi kết nối máy chủ khi lấy danh sách môn học.");
      });
  }

  function queryAnalyticsData() {
    const subjectId = anSubjectSelect.value;
    const tryNumber = anTryNumberSelect.value;

    if (!subjectId) return;

    document.getElementById("analyticsContent").classList.add("d-none");
    document.getElementById("analyticsError").classList.add("d-none");
    document.getElementById("analyticsLoading").classList.remove("d-none");

    const url = `${window.APP_BASE_PATH}/classrooms/${currentClassId}/analytics?subjectId=${encodeURIComponent(subjectId)}&tryNumber=${tryNumber}`;

    fetch(url)
      .then(res => res.json())
      .then(data => {
        document.getElementById("analyticsLoading").classList.add("d-none");
        if (data.error) {
          showError(data.error);
          return;
        }

        renderAnalytics(data);
      })
      .catch(err => {
        console.error(err);
        document.getElementById("analyticsLoading").classList.add("d-none");
        showError("Không thể tải thông tin thống kê học tập.");
      });
  }

  function renderAnalytics(data) {
    // 1. Render KPI values
    document.getElementById("anTotalStudents").innerText = data.totalStudents;
    document.getElementById("anAttempted").innerText = data.attemptedCount;
    document.getElementById("anNotAttempted").innerText = data.notAttemptedCount;
    document.getElementById("anAverageScore").innerText = data.averageScore.toFixed(2);
    
    const passRate = data.attemptedCount > 0 
      ? Math.round((data.passCount / data.attemptedCount) * 100) 
      : 0;
    document.getElementById("anPassRate").innerText = `${passRate}%`;

    // 2. Render Top Students
    const topBody = document.getElementById("anTopStudentsBody");
    topBody.innerHTML = "";
    if (data.topStudents && data.topStudents.length > 0) {
      data.topStudents.forEach((student, index) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td><span class="badge ${index === 0 ? 'bg-warning' : 'bg-secondary'}">${index + 1}</span></td>
          <td>${student.studentId}</td>
          <td>${student.fullName}</td>
          <td class="text-end fw-bold text-primary">${student.score.toFixed(1)} (${student.grade})</td>
        `;
        topBody.appendChild(tr);
      });
    } else {
      topBody.innerHTML = `<tr><td colspan="4" class="text-center text-muted">Chưa có sinh viên nào làm bài thi.</td></tr>`;
    }

    // 3. Render Chart
    const ctx = document.getElementById("gradeDistributionChart").getContext("2d");
    const grades = ["A+", "A", "B+", "B", "C+", "C", "D+", "D", "F"];
    const chartData = grades.map(g => data.distribution[g] || 0);

    if (gradeChartInstance) {
      gradeChartInstance.destroy();
    }

    gradeChartInstance = new Chart(ctx, {
      type: "bar",
      data: {
        labels: grades,
        datasets: [{
          label: "Số lượng sinh viên",
          data: chartData,
          backgroundColor: "rgba(13, 110, 253, 0.6)",
          borderColor: "rgb(13, 110, 253)",
          borderWidth: 1.5,
          borderRadius: 4
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              stepSize: 1,
              precision: 0
            },
            grid: {
              color: "rgba(0, 0, 0, 0.05)"
            }
          },
          x: {
            grid: {
              display: false
            }
          }
        }
      }
    });

    document.getElementById("analyticsContent").classList.remove("d-none");
  }

  function showError(msg) {
    const errorDiv = document.getElementById("analyticsError");
    errorDiv.innerText = msg;
    errorDiv.classList.remove("d-none");
  }
});
