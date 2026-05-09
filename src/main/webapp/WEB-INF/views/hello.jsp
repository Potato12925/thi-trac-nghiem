<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello Page</title>
    <!-- Include Bootstrap as per guidelines -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">Welcome to Spring MVC</h4>
                    </div>
                    <div class="card-body">
                        <!-- Using EL as per guidelines -->
                        <h1 class="text-center text-success">${message}</h1>
                        <p class="text-center mt-4">
                            Giao diện Hello được tạo theo quy tắc trong <strong>agent.md</strong>:
                        </p>
                        <ul class="list-group list-group-flush mt-3">
                            <li class="list-group-item">✔️ Sử dụng Bootstrap cho UI</li>
                            <li class="list-group-item">✔️ Sử dụng JSP + JSTL + Expression Language</li>
                            <li class="list-group-item">✔️ Đặt JSP vào đúng thư mục <code>/WEB-INF/views</code></li>
                            <li class="list-group-item">✔️ Sử dụng Annotation <code>@Controller</code> trong Spring</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
