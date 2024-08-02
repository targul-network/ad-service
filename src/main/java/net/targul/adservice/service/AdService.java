package net.targul.adservice.service;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;

import java.util.List;

public interface AdService {
    List<AdDto> getFilteredAds();
    AdDto createAd(AdRequest adRequest);
    AdDto updateAd(String id, AdRequest adRequest);
    void activateAd(String id);
    void deactivateAd(String id);
    void archiveAd(String id);
    void banAd(String id);
    void deleteAd(String id);
}