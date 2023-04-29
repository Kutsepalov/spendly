package com.acceleron.spendly.core.util;

public final class UserAuthenticationMatcher {

    private UserAuthenticationMatcher() {

    }

    public static final String EMAIL_PATTERN = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";

    public static boolean isEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean isUsername(String username) {
        return username.matches(USERNAME_PATTERN);
    }
}
