package com.gradproject2019.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameUtils {

    private static final String PATTERN = "^[a-zA-Z0-9]*$";

    public static boolean validate(final String username) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
