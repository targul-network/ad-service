package net.targul.adservice.service.impl;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.entity.Ad;
import net.targul.adservice.entity.AdStatus;
import net.targul.adservice.exception.EntityNotFoundException;
import net.targul.adservice.exception.ad.AdServiceBusinessException;
import net.targul.adservice.exception.ad.AdServiceDataException;
import net.targul.adservice.mapper.AdMapper;
import net.targul.adservice.repository.AdRepository;
import net.targul.adservice.service.AdService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {
    private static final Logger log = LoggerFactory.getLogger(AdServiceImpl.class);
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    @Override
    @Transactional
    public AdDto createAd(AdRequest adRequest) {
        log.info("AdService::createAd execution has been started.");
        log.debug("AdService::createAd request parameters {}", adRequest);

        try {
            Ad ad = adMapper.toEntity(adRequest);

            ad.setStatus(AdStatus.PENDING);

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
        updatedAd.setStatus(AdStatus.PENDING);

        Ad savedAd = adRepository.save(updatedAd);
        AdDto savedAdDto = adMapper.toDto(savedAd);

        log.info("AdService::updateAd: ad with id {} has been updated successfully: {}", id, savedAdDto);
        log.info("AdService::updateAd execution has ended.");
        return savedAdDto;
    }

    @Override
    @Transactional
    public void activateAd(String id) {
        Optional<Ad> adOptional = adRepository.findById(id);
        if (adOptional.isEmpty()) {
            throw new EntityNotFoundException("Unable to activate nonexistent Ad entity with ID: " + id);
        }

        Ad adToArchive = adOptional.get();

        if(!adToArchive.getStatus().equals(AdStatus.ACTIVE)) {
            adToArchive.setStatus(AdStatus.ACTIVE);
            adRepository.save(adToArchive);
        }
    }

    @Override
    @Transactional
    public void deactivateAd(String id) {
        Optional<Ad> adOptional = adRepository.findById(id);
        if (adOptional.isEmpty()) {
            throw new EntityNotFoundException("Unable to deactivate nonexistent Ad entity with ID: " + id);
        }

        Ad adToArchive = adOptional.get();

        if(!adToArchive.getStatus().equals(AdStatus.INACTIVE)) {
            adToArchive.setStatus(AdStatus.INACTIVE);
            adRepository.save(adToArchive);
        }
    }

    @Override
    @Transactional
    public void archiveAd(String id) {
        log.info("AdService::archiveAd execution has started.");
        log.debug("AdService::archiveAd ad id {}", id);

        Optional<Ad> adOptional = adRepository.findById(id);

        if (adOptional.isEmpty()) {
            log.error("AdService::archiveAd: Unable to archive nonexistent Ad entity with ID: {}", id);
            throw new EntityNotFoundException("Unable to archive nonexistent Ad entity with ID: " + id);
        }

        Ad adToArchive = adOptional.get();
        adToArchive.setStatus(AdStatus.ARCHIVED);
        adRepository.save(adToArchive);

        log.info("AdService::archiveAd: ad with id {} has been archived successfully", id);
        log.info("AdService::archiveAd execution has ended.");
    }

    @Override
    @Transactional
    public void banAd(String id) {
        Optional<Ad> adOptional = adRepository.findById(id);
        if (adOptional.isEmpty()) {
            throw new EntityNotFoundException("Unable to ban nonexistent Ad entity with ID: " + id);
        }

        Ad adToBan = adOptional.get();

        if(!adToBan.getStatus().equals(AdStatus.BANNED)) {
            adToBan.setStatus(AdStatus.BANNED);
            adRepository.save(adToBan);
        }
    }

    @Override
    public void deleteAd(String id) {

        if (adRepository.existsById(id)) {
            adRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Unable to delete nonexistent Ad entity with ID: " + id);
        }
    }
}