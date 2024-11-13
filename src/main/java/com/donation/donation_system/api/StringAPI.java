package com.donation.donation_system.api;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static javax.swing.text.html.parser.DTDConstants.NUMBERS;
import static utils.Constants.*;

public class StringAPI {
    public static String generateRandomPassword(int len) {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String combination = upperChars + lowerChars + numbers;
        char password[] = new char[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            password[i] = combination.charAt(r.nextInt(combination.length()));
        }
        return new String(password);
    }

    public static String generateStrongPassword(int length) {
        String allChars = UPPERCASE_CHARS + LOWERCASE_CHARS + NUMBERS + SPECIAL_CHARS;
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Đảm bảo có ít nhất một ký tự từ mỗi loại
        password.append(getRandomChar(UPPERCASE_CHARS, random));
        password.append(getRandomChar(LOWERCASE_CHARS, random));
        password.append(getRandomChar(String.valueOf(NUMBERS), random));
        password.append(getRandomChar(SPECIAL_CHARS, random));

        // Tạo các ký tự còn lại ngẫu nhiên
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Trộn các ký tự để tăng độ phức tạp
        List<Character> chars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars);
        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : chars) {
            shuffledPassword.append(c);
        }

        return shuffledPassword.toString();
    }

    private static char getRandomChar(String chars, Random random) {
        return chars.charAt(random.nextInt(chars.length()));
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(password.getBytes());
        byte[] digest = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }
    public static String encodePassword(String password) throws NoSuchAlgorithmException {
        return DigestUtils.md5Hex(password);
    }
//    public static String encodePassword(String password) throws NoSuchAlgorithmException {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder.encode(password);
//    }
}
