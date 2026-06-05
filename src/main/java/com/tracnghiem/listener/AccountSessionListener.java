package com.tracnghiem.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tracnghiem.service.AuthService;

public class AccountSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		String username = (String) session.getAttribute("LOGIN_USER");
		if (username == null) {
			return;
		}

		WebApplicationContext context = WebApplicationContextUtils.findWebApplicationContext(session.getServletContext());
		if (context == null) {
			return;
		}

		AuthService authService = context.getBean(AuthService.class);
		authService.clearLoginSession(username, session.getId());
	}
}
