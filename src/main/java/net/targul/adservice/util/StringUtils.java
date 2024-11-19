package net.targul.adservice.util;

public class StringUtils {

    public static String clearExtraSpaces(String str) {
        return str.trim().replaceAll("\\s+", " ");
    }
}