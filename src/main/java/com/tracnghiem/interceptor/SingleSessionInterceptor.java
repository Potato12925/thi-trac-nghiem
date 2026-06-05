package com.tracnghiem.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tracnghiem.service.AuthService;
import com.tracnghiem.utils.RoleNavigationUtils;

public class SingleSessionInterceptor implements HandlerInterceptor {

	@Autowired
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		String username = session != null ? (String) session.getAttribute("LOGIN_USER") : null;
		String role = session != null ? (String) session.getAttribute("ROLE") : null;

		if (session == null || username == null || !RoleNavigationUtils.isAuthenticatedRole(role)) {
			return true;
		}

		String sessionId = session.getId();
		if (!authService.isCurrentSessionValid(username, sessionId)) {
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/auth/login?sessionExpired=1");
			return false;
		}

		authService.touchCurrentSession(username, sessionId);
		return true;
	}
}
