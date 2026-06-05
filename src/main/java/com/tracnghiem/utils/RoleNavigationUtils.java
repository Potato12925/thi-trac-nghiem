package com.tracnghiem.utils;

public final class RoleNavigationUtils {

	private RoleNavigationUtils() {
	}

	public static String getHomePath(String role) {
		if (RoleConstants.PGV.equals(role)) {
			return "/admin/home";
		}

		if (RoleConstants.LECTURER.equals(role)) {
			return "/lecturers/home";
		}

		if (RoleConstants.STUDENT.equals(role)) {
			return "/students/home";
		}

		return "/auth/login";
	}

	public static String getHomeRedirect(String role) {
		return "redirect:" + getHomePath(role);
	}

	public static boolean isPgv(String role) {
		return RoleConstants.PGV.equals(role);
	}

	public static boolean isLecturer(String role) {
		return RoleConstants.LECTURER.equals(role);
	}

	public static boolean isStudent(String role) {
		return RoleConstants.STUDENT.equals(role);
	}

	public static boolean isAuthenticatedRole(String role) {
		return isPgv(role) || isLecturer(role) || isStudent(role);
	}
}
