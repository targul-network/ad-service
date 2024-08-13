package net.targul.adservice.util.id.base62.impl;

import net.targul.adservice.util.id.base62.Base62;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Map;
import java.util.HashMap;

@Component
public class ObjectIdBase62 implements Base62<ObjectId> {

    private final Map<String, ObjectId> shortIdToObjectIdMap = new HashMap<>();

    // Encodes a MongoDB ObjectId into a Base62 short ID
    public String encode(ObjectId objectId) {
        byte[] idBytes = objectId.toByteArray();
        BigInteger bigInt = new BigInteger(1, idBytes);
        StringBuilder base62 = new StringBuilder();
        while (bigInt.compareTo(BigInteger.ZERO) > 0) {
            int index = bigInt.mod(BigInteger.valueOf(BASE62_RADIX)).intValue();
            base62.insert(0, BASE62_CHARSET.charAt(index));
            bigInt = bigInt.shiftRight(6);
        }
        String shortId = base62.toString();
        shortIdToObjectIdMap.put(shortId, objectId);
        return shortId;
    }

    // Decodes a Base62 short ID back into a MongoDB ObjectId
    public ObjectId decode(String shortId) {
        if (shortIdToObjectIdMap.containsKey(shortId)) {
            return shortIdToObjectIdMap.get(shortId);
        }

        BigInteger bigInt = BigInteger.ZERO;
        for (char c : shortId.toCharArray()) {
            bigInt = bigInt.multiply(BigInteger.valueOf(BASE62_RADIX))
                    .add(BigInteger.valueOf(BASE62_CHARSET.indexOf(c)));
        }
        byte[] idBytes = bigInt.toByteArray();
        // Handle case where the array starts with zero byte
        if (idBytes.length > 16) {
            byte[] temp = new byte[16];
            System.arraycopy(idBytes, idBytes.length - 16, temp, 0, 16);
            idBytes = temp;
        }

        ObjectId objectId = new ObjectId(idBytes);
        shortIdToObjectIdMap.put(shortId, objectId);
        return objectId;
    }
}