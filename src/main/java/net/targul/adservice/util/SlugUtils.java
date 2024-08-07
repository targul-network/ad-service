package net.targul.adservice.util;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Component;

@Component
public class SlugUtils {

    private static final String LATIN_TO_ASCII = "Any-Latin; Latin-ASCII";

    public String generateSlug(String input) {
        // Transliteration
        Transliterator transliterator = Transliterator.getInstance(LATIN_TO_ASCII);
        String result = transliterator.transliterate(input);

        // Lower case
        result = result.toLowerCase();

        // Special symbols and spaces replacement on - symbol
        result = result.replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");

        return result;
    }
}