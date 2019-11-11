package com.gradproject2019.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PasswordUtils {

    private static final String PATTERN = "((?=.*[a-z])(?=.*[0-9])(?=.*[!?\\#@^&£$*+;:~])(?=.*[A-Z]).{8,16})";

    public static boolean validate(final String password) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String hash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}