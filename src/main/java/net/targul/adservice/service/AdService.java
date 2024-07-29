package net.targul.adservice.service;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;

import java.util.List;

public interface AdService {
    List<AdDto> getAllAds();
    AdDto createAd(AdRequest adRequest);
}