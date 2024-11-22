package net.targul.adservice.service;

import java.util.List;
import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import org.springframework.http.ResponseEntity;

public interface AdService {

    AdDto getAdByPid(Long id);
    List<AdDto> getActiveAdsByPage(int page);
    AdDto createAd(AdRequest adRequest);
    AdDto updateAd(Long id, AdRequest adRequest);
    ResponseEntity<String> activateAd(Long id);
    void deactivateAd(Long id);
    void archiveAd(Long id);
    void banAd(Long id);
    void deleteAd(Long id);
}