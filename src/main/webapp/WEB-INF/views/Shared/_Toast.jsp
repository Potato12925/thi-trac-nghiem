<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div aria-live="polite" aria-atomic="true" class="position-relative">
    <div class="toast-container position-fixed top-0 end-0 p-3">
        <c:if test="${not empty successMessage}">
            <div id="successToast" class="toast align-items-center text-bg-success border-0 shadow" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body fw-medium">
                        <i class="bi bi-check-circle-fill me-2"></i> ${successMessage}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    var t = document.getElementById('successToast');
                    var toast = new bootstrap.Toast(t, { delay: 5000 });
                    toast.show();
                });
            </script>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div id="errorToast" class="toast align-items-center text-bg-danger border-0 shadow" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body fw-medium">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i> ${errorMessage}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    var t = document.getElementById('errorToast');
                    var toast = new bootstrap.Toast(t, { delay: 5000 });
                    toast.show();
                });
            </script>
        </c:if>
    </div>
</div>
