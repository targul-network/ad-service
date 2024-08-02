package net.targul.adservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.entity.Ad;
import net.targul.adservice.exception.EntityNotFoundException;
import net.targul.adservice.exception.ad.AdServiceBusinessException;
import net.targul.adservice.exception.ad.AdServiceDataException;
import net.targul.adservice.mapper.AdMapper;
import net.targul.adservice.repository.AdRepository;
import net.targul.adservice.service.AdService;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;

    @Override
    @Transactional
    public AdDto createAd(AdRequest adRequest) {
        log.info("AdService::createAd execution has been started.");
        log.debug("AdService::createAd request parameters {}", adRequest);

        try {
            Ad ad = adMapper.toEntity(adRequest);
            Ad savedAd = adRepository.save(ad);
            AdDto adDto = adMapper.toDto(savedAd);
            log.debug("AdService::createAd received response from Database {}", savedAd);
            log.info("AdService::createAd execution has been ended.");
            return adDto;
        } catch (DataAccessException dae) {
            log.error("Database exception occurred while persisting product: {}", dae.getMessage());
            throw new AdServiceDataException("Database exception occurred while creating new ad", dae);
        } catch (Exception e) {
            log.error("Unexpected exception occurred while creating ad: {}", e.getMessage());
            throw new AdServiceBusinessException("Unexpected exception occurred while creating new ad", e);
        }
    }

    @Override
    @Transactional
    public AdDto updateAd(String id, AdRequest adRequest) {
        log.info("AdService::updateAd execution has started.");
        log.debug("AdService::updateAd request parameters {}", adRequest);

        if (!adRepository.existsById(id)) {
            log.error("AdService::updateAd: Unable to update nonexistent entity with ID: {}", id);
            throw new EntityNotFoundException("Unable to update nonexistent entity with ID: " + id);
        }

        Ad updatedAd = adMapper.toEntity(adRequest);
        updatedAd.setId(id);
        Ad savedAd = adRepository.save(updatedAd);
        AdDto savedAdDto = adMapper.toDto(savedAd);

        log.info("AdService::updateAd: ad with id {} has been updated successfully: {}", id, savedAdDto);
        log.info("AdService::updateAd execution has ended.");
        return savedAdDto;
    }
}