package net.targul.adservice.util;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;

@Component
public class SlugUtils {

//    private final TransliterationUtils transliterationUtils;
//
//    public SlugUtils(TransliterationUtils transliterationUtils) {
//        this.transliterationUtils = transliterationUtils;
//    }

    public String createSlug(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

//        input = transliterationUtils.transliterateRuRoStr(input); Todo

        // Normalize the string to remove accents and diacritics
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Remove non-ASCII characters and accents
        String cleaned = normalized.replaceAll("[^\\p{ASCII}]", "");
        // Convert to lowercase
        String lowerCased = cleaned.toLowerCase(Locale.ENGLISH);
        // Replace spaces and special characters with hyphens
        String slug = lowerCased.replaceAll("[\\s+]", "-") // Replace spaces with hyphens
                .replaceAll("[^-a-z0-9]", ""); // Remove remaining special characters

        return slug;
    }
}