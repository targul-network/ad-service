package net.targul.adservice.util;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
public class IdUtils {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    public static String encode(byte[] input) {
        StringBuilder result = new StringBuilder();
        int num = 0;
        for (int i = 0; i < input.length; i++) {
            num = (num << 8) | (input[i] & 0xFF);
            while (num >= BASE) {
                result.append(BASE62.charAt(num % BASE));
                num /= BASE;
            }
        }
        if (num > 0) {
            result.append(BASE62.charAt(num));
        }
        return result.reverse().toString();
    }

    public static byte[] decode(String input) {
        byte[] bytes = new byte[16];
        long num = 0;
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            num = num * BASE + BASE62.indexOf(input.charAt(i));
            if ((i + 1) % 2 == 0) {
                for (int j = 0; j < 8 && index < 16; j++) {
                    bytes[index++] = (byte) ((num >> (8 * (7 - j))) & 0xFF);
                }
                num = 0;
            }
        }
        return bytes;
    }

    public static String encodeUUID(UUID uuid) {
        byte[] bytes = new byte[16];
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) ((mostSigBits >> (8 * (7 - i))) & 0xFF);
            bytes[8 + i] = (byte) ((leastSigBits >> (8 * (7 - i))) & 0xFF);
        }
        return encode(bytes);
    }

    public static UUID decodeUUID(String base62) {
        byte[] bytes = decode(base62);
        long mostSigBits = 0;
        long leastSigBits = 0;
        for (int i = 0; i < 8; i++) {
            mostSigBits = (mostSigBits << 8) | (bytes[i] & 0xFF);
            leastSigBits = (leastSigBits << 8) | (bytes[8 + i] & 0xFF);
        }
        return new UUID(mostSigBits, leastSigBits);
    }
}
