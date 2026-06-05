package com.tracnghiem.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.AntPathMatcher;

public final class RoleAccessRules {

	private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

	private static final List<String> LECTURER_ALLOWED_PATTERNS = Collections.unmodifiableList(Arrays.asList(
			"/lecturers/home",
			"/lecturers/settings",
			"/lecturers/settings/**",
			"/auth/change-password",
			"/questions",
			"/questions/add",
			"/questions/update",
			"/questions/delete",
			"/questions/save",
			"/questions/check-duplicate",
			"/questions/upload-image",
			"/questions/export",
			"/lecturer-registration",
			"/lecturer-registration/add",
			"/lecturer-registration/update",
			"/lecturer-registration/delete",
			"/scores",
			"/history/detail"));

	private static final List<String> STUDENT_ALLOWED_PATTERNS = Collections.unmodifiableList(Arrays.asList(
			"/students/home",
			"/students/settings",
			"/students/settings/**",
			"/auth/change-password",
			"/exam",
			"/exam/**",
			"/history",
			"/history/detail"));

	private RoleAccessRules() {
	}

	public static boolean isAllowed(String role, String path) {
		if (RoleNavigationUtils.isPgv(role)) {
			return true;
		}

		if (RoleNavigationUtils.isLecturer(role)) {
			return matchesAny(LECTURER_ALLOWED_PATTERNS, path);
		}

		if (RoleNavigationUtils.isStudent(role)) {
			return matchesAny(STUDENT_ALLOWED_PATTERNS, path);
		}

		return false;
	}

	private static boolean matchesAny(List<String> patterns, String path) {
		for (String pattern : patterns) {
			if (PATH_MATCHER.match(pattern, path)) {
				return true;
			}
		}
		return false;
	}
}
