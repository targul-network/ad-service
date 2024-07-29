package net.targul.adservice.service;

import net.targul.adservice.dto.ad.AdRequestDto;
import net.targul.adservice.dto.ad.AdDto;

import java.util.List;

public interface AdService {
    List<AdDto> getAllAds();
    AdDto createAd(AdRequestDto adRequestDTO);
}