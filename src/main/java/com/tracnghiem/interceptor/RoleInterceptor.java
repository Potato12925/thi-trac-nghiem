package com.tracnghiem.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.tracnghiem.utils.RoleAccessRules;
import com.tracnghiem.utils.RoleNavigationUtils;

public class RoleInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);
		String role = session != null ? (String) session.getAttribute("ROLE") : null;

		if (!RoleNavigationUtils.isAuthenticatedRole(role)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return false;
		}

		String path = normalizePath(request);
		if (RoleAccessRules.isAllowed(role, path)) {
			return true;
		}

		String targetPath = RoleNavigationUtils.getHomePath(role);
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("errorMessage", "Bạn không có quyền truy cập chức năng này");
		RequestContextUtils.saveOutputFlashMap(targetPath, request, response);
		response.sendRedirect(request.getContextPath() + targetPath);
		return false;
	}

	private String normalizePath(HttpServletRequest request) {
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();

		if (path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}

		if (path == null || path.isEmpty()) {
			return "/";
		}

		if (path.length() > 1 && path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		}

		return path;
	}
}
