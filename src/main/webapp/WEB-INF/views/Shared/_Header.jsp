<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <% String username =
session.getAttribute("LOGIN_USER") != null ?
session.getAttribute("LOGIN_USER").toString() : "User"; String role =
session.getAttribute("ROLE") != null ? session.getAttribute("ROLE").toString() :
""; String initial = username.length() > 0 ? username.substring(0,
1).toUpperCase() : "U"; String pageTitle = (String)
request.getAttribute("pageTitle"); if (pageTitle == null) pageTitle =
"QLDSV_HTC"; %>
<header class="app-header">
  <div class="d-flex align-items-center gap-3">
    <button
      class="btn d-lg-none"
      id="sidebarToggle"
      type="button"
      aria-label="Toggle Navigation Sidebar"
      aria-expanded="false"
      aria-controls="appSidebar"
    >
      <i class="bi bi-list fs-4" aria-hidden="true"></i>
    </button>
    <h1 class="h4 mb-0"><%= pageTitle %></h1>
  </div>
  <div
    class="header-user"
    tabindex="0"
    role="button"
    aria-haspopup="true"
    aria-expanded="false"
  >
    <div class="header-user-info d-none d-sm-block text-end">
      <div class="name"><%= username %></div>
      <div class="role"><%= role %></div>
    </div>
    <div class="header-avatar" aria-hidden="true"><%= initial %></div>
  </div>
</header>
