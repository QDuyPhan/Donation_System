package com.donation.donation_system.api;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static utils.Constants.*;

public class StringAPI {
    public static String generateStrongPassword(int length) {
        String allChars = UPPERCASE_CHARS + LOWERCASE_CHARS + NUMBERS;
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Đảm bảo có ít nhất một ký tự từ mỗi loại
        password.append(getRandomChar(UPPERCASE_CHARS, random));
        password.append(getRandomChar(LOWERCASE_CHARS, random));
        password.append(getRandomChar(String.valueOf(NUMBERS), random));
//        password.append(getRandomChar(SPECIAL_CHARS, random));

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

    public static String encodePassword(String password) throws NoSuchAlgorithmException {
        return DigestUtils.md5Hex(password);
    }

}
