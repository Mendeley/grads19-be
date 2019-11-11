package com.gradproject2019.users.service;

import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordService {

    private static final String PATTERN = "((?=.*[a-z])(?=.*[0-9])(?=.*[!?\\#@^&£$*+;:~])(?=.*[A-Z]).{8,16})";

    public boolean validate(final String password) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public String hash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}