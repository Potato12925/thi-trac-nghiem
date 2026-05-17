package com.tracnghiem.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        Object user = request.getSession().getAttribute("LOGIN_USER");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/hello");
            return false;
        }

        return true;
    }
}