package net.targul.adservice.exception.ad;

public class AdServiceBusinessException extends RuntimeException {
    public AdServiceBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}