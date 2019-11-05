package com.gradproject2019.users.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Validator INSTANCE = new Validator();
    static String pattern = null;

    private Validator() {
    }

    public static Validator buildValidator() {
        pattern = "((?=.*[a-z])(?=.*[0-9])(?=.*[!?\\#@^&£$*+;:~])(?=.*[A-Z]).{8,16})";
        return INSTANCE;
    }

    public static boolean validate(final String input) {
        buildValidator();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        return m.matches();
    }
}