package com.servitization.session.util;


public class TokenValidateHelper {

    public static boolean validate(String token) {
        return getTypeFromToken(token) != -1;
    }

    private static int getTypeFromToken(String token) {
        char lastChar = getTypeNum(token);
        if (!isO_9(lastChar))
            return -1;
        return getTypeByNum(charToInt(lastChar), token);
    }

    private static char getTypeNum(String token) {
        return token.substring(token.length() - 1).charAt(0);
    }

    private static int getTypeByNum(int num, String token) {
        if (num == 0)
            return -1;
        if (token.length() - 1 - num < 0)
            return -1;
        String typeString = token.substring(token.length() - 1 - num,
                token.length() - 1);
        if (isDigit(typeString)) {
            return Integer.valueOf(typeString);
        } else {
            return -1;
        }
    }

    private static int charToInt(char c) {
        return c - 48;
    }

    private static boolean isO_9(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isDigit(String src) {
        boolean result = true;
        for (int i = 0; i < src.length() && result; result = isO_9(src
                .charAt(i++)))
            ;
        return result;
    }
}
