package net.targul.adservice.service;

import java.util.List;
import java.util.UUID;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import org.springframework.http.ResponseEntity;

public interface AdService {

    AdDto getAdById(UUID id);
    List<AdDto> getActiveAdsByPage(int page);
    AdDto createAd(AdRequest adRequest);
    AdDto updateAd(UUID id, AdRequest adRequest);
    ResponseEntity<String> activateAd(UUID id);
    void deactivateAd(UUID id);
    void archiveAd(UUID id);
    void banAd(UUID id);
    void deleteAd(UUID id);
}