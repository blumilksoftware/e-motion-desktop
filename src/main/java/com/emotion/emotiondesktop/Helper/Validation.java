package com.emotion.emotiondesktop.Helper;

public class Validation {
    public static boolean isValidNumber(String input) {
        return input.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static boolean isNotEmpty(String... inputs) {
        for (String input : inputs) {
            if (input.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
