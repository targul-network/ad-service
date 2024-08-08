package net.targul.adservice.util.id.base62;

public interface Base62 <T> {

    String BASE62_CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int BASE62_RADIX = BASE62_CHARSET.length();

    String encode(T id);
    T decode(String id);
}