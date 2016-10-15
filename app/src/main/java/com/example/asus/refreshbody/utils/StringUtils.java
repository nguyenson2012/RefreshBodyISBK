package com.example.asus.refreshbody.utils;

import android.util.Patterns;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by linhdq on 9/19/16.
 */
public class StringUtils {
    public static boolean isStringEmpty(String s) {
        if (s != null && s.toCharArray().length > 0) {
            return false;
        }
        return true;
    }

    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }

    public static boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateName(String name) {
        if (name == null || name.toCharArray().length == 0) return false;
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') return false;
        }
        return true;
    }


    public static boolean validatePassword(String password) {
        if (password == null || password.toCharArray().length < 6) return false;
        for (char c : password.toCharArray()) {
            if (c == ' ') {
                return false;
            }
        }
        return true;
    }
}
