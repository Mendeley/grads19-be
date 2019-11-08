package com.gradproject2019.users.service;

import com.gradproject2019.users.persistance.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hashing {
    public static final String ALGORITHM = "SHA-256";

    public static void main(String[] args) throws Exception {

        //String password = User.getPassword();
        String password = "Grace123^^^^";

        //this will eventually push the data to the database
        System.out.println("SHA-256 Hash : " + generateHash(password));
    }

    public static String generateHash(String password) throws NoSuchAlgorithmException {
        byte[] salt = createSalt();
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.reset();
        md.update(salt);
        byte[] hash = md.digest(password.getBytes());
        return bytesToHex(hash);
    }


//        public String hash(String password){
//            return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
//        }

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
