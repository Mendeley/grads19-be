package com.gradproject2019.users.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gradproject2019.users.persistance.User;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordService {

    private static final String ALGORITHM = "SHA-256";
    private static final String PATTERN = "((?=.*[a-z])(?=.*[0-9])(?=.*[!?\\#@^&£$*+;:~])(?=.*[A-Z]).{8,16})";

    public boolean validate(final String password) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public String generateHash(String password)  {
        byte[] salt = createSalt();
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.reset();
            md.update(salt);
            byte[] hash = md.digest(password.getBytes());
            return bytesToHex(hash);
        } catch(NoSuchAlgorithmException exception) {
            return exception.getMessage();
        }
    }

        public String hash(String password){
            return BCrypt.hashpw(password, BCrypt.gensalt());
        }

    //this is a custom byte to hex converter to get the hash in hexadecimal
    private static String bytesToHex(byte[] hash) {
        //StringBuffer is a string of undefined length
        StringBuffer hexString = new StringBuffer();
        for(int i = 0; i < hash.length; i++){
            String hex = Integer.toHexString(0xFF & hash[i]);
            //not sure why we cant have a hex of length 1
            if(hex.length()== 1 ){
                hexString.append("0");
            }
            hexString.append(hex);
        }
        return(hexString.toString());
    }

    private static byte[] createSalt() {
        byte[] bytes = new byte[35];
        SecureRandom random = new SecureRandom();
        //fills up the empty bytes
        random.nextBytes(bytes);
        return(bytes);
    }
}
