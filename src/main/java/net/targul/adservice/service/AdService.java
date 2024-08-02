package net.targul.adservice.service;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;

public interface AdService {
    AdDto createAd(AdRequest adRequest);
    AdDto updateAd(String id, AdRequest adRequest);
    void archiveAd(String id);
}