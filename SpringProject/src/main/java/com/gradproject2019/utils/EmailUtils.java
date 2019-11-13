package com.gradproject2019.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    private static final String PATTERN = "^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`]+@[a-zA-Z0-9]+\\.[\\.A-Za-z]{1,10}";

    public static boolean validate(final String email) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
