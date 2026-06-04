            </main>
        </div>
    </div>
    <jsp:include page="/WEB-INF/views/Shared/_Modal.jsp" />
    <jsp:include page="/WEB-INF/views/Shared/_Toast.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var toggleBtn = document.getElementById('sidebarToggle');
            var sidebar = document.getElementById('appSidebar');
            if (toggleBtn && sidebar) {
                toggleBtn.addEventListener('click', function() {
                    sidebar.classList.toggle('show');
                });
            }
        });
    </script>
    <c:if test="${not empty customJs}">
        <script src="${pageContext.request.contextPath}/assets/js/${customJs}"></script>
    </c:if>
</body>
</html>
