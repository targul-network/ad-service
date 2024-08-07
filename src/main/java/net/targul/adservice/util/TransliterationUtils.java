//package net.targul.adservice.util;
//
//import com.ibm.icu.text.Transliterator;
//import org.springframework.stereotype.Component;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Component
//public class TransliterationUtils {
// TODO
//    private static final Transliterator RUSSIAN_TO_LATIN = Transliterator.getInstance("Russian-Latin/BGN");
//    private static final Transliterator ROMANIAN_TO_LATIN = Transliterator.getInstance("Latin-Romanian");
//
//    public String transliterateRuRoStr(String input) {
//        if (input == null || input.isEmpty()) {
//            return "";
//        }
//
//        StringBuilder result = new StringBuilder();
//
//        // Define regex patterns for Romanian and Russian text
//        Pattern romanianPattern = Pattern.compile("[\\p{L}\\p{M}]+", Pattern.UNICODE_CHARACTER_CLASS);
//        Pattern russianPattern = Pattern.compile("[\\p{InCyrillic}]+", Pattern.UNICODE_CHARACTER_CLASS);
//
//        Matcher matcher = romanianPattern.matcher(input);
//        int lastEnd = 0;
//
//        // Process Romanian parts
//        while (matcher.find()) {
//            result.append(input, lastEnd, matcher.start());
//            result.append(ROMANIAN_TO_LATIN.transliterate(matcher.group()));
//            lastEnd = matcher.end();
//        }
//
//        // Process Russian parts
//        matcher = russianPattern.matcher(input);
//        while (matcher.find()) {
//            result.append(input, lastEnd, matcher.start());
//            result.append(RUSSIAN_TO_LATIN.transliterate(matcher.group()));
//            lastEnd = matcher.end();
//        }
//
//        // Append the remaining part of the input
//        result.append(input.substring(lastEnd));
//
//        return result.toString();
//    }
//}