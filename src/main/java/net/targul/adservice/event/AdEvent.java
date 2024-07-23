package net.targul.adservice.event;

import net.targul.adservice.model.Ad;

public class AdEvent {
    private String type;
    private Ad ad;

    public AdEvent(String type, Ad ad) {
        this.type = type;
        this.ad = ad;
    }
}
