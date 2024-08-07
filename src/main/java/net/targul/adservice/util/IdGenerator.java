package net.targul.adservice.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String generateShortId() {
        return IdUtils.encodeUUID(UUID.randomUUID());
    }
}
