package net.targul.adservice.service;

import java.util.List;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;

public interface AdService {

    AdDto getAdBySlugAndShortId(String slug, String shortId);

    List<AdDto> getActiveAdsByPage(int page);

    AdDto createAd(AdRequest adRequest);

    AdDto updateAd(String id, AdRequest adRequest);

    void activateAd(String id);

    void deactivateAd(String id);

    void archiveAd(String id);

    void banAd(String id);

    void deleteAd(String id);
}