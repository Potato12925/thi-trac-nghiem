package com.tracnghiem.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.tracnghiem.utils.RoleNavigationUtils;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);
		String username = session != null ? (String) session.getAttribute("LOGIN_USER") : null;
		String role = session != null ? (String) session.getAttribute("ROLE") : null;

		if (username == null || !RoleNavigationUtils.isAuthenticatedRole(role)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return false;
		}

		return true;
	}
}
