package net.targul.adservice.util.id.base62.impl;

import net.targul.adservice.util.id.base62.Base62;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidBase62 implements Base62<UUID> {

    public String encode(UUID uuid) {
        byte[] bytes = new byte[16];
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) ((mostSigBits >> (8 * (7 - i))) & 0xFF);
            bytes[8 + i] = (byte) ((leastSigBits >> (8 * (7 - i))) & 0xFF);
        }
        return encodeBytes(bytes);
    }

    public UUID decode(String base62) {
        byte[] bytes = decodeBytes(base62);
        long mostSigBits = 0;
        long leastSigBits = 0;
        for (int i = 0; i < 8; i++) {
            mostSigBits = (mostSigBits << 8) | (bytes[i] & 0xFF);
            leastSigBits = (leastSigBits << 8) | (bytes[8 + i] & 0xFF);
        }
        return new UUID(mostSigBits, leastSigBits);
    }

    private String encodeBytes(byte[] input) {
        StringBuilder result = new StringBuilder();
        int num = 0;
        for (byte b : input) {
            num = (num << 8) | (b & 0xFF);
            while (num >= BASE62_RADIX) {
                result.append(BASE62_CHARSET.charAt(num % BASE62_RADIX));
                num /= BASE62_RADIX;
            }
        }
        if (num > 0) {
            result.append(BASE62_CHARSET.charAt(num));
        }
        return result.reverse().toString();
    }

    private byte[] decodeBytes(String input) {
        byte[] bytes = new byte[16];
        long num = 0;
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            num = num * BASE62_RADIX + BASE62_CHARSET.indexOf(input.charAt(i));
            if ((i + 1) % 2 == 0) {
                for (int j = 0; j < 8 && index < 16; j++) {
                    bytes[index++] = (byte) ((num >> (8 * (7 - j))) & 0xFF);
                }
                num = 0;
            }
        }
        return bytes;
    }
}