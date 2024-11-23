package net.targul.adservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.domain.ad.Ad;
import net.targul.adservice.domain.ad.AdStatus;
import net.targul.adservice.domain.Category;
import net.targul.adservice.service.exception.AdNotFoundException;
import net.targul.adservice.service.exception.EntityNotFoundException;
import net.targul.adservice.mapper.AdMapper;
import net.targul.adservice.repository.AdRepository;
import net.targul.adservice.repository.CategoryRepository;
import net.targul.adservice.service.AdService;

import net.targul.adservice.util.id.base62.impl.ObjectIdBase62;
import net.targul.adservice.util.SlugUtils;
import net.targul.adservice.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdServiceImpl implements AdService {

    private final int ADS_PER_PAGE;
    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final AdMapper adMapper;
    private final SlugUtils slugUtils;

    @Override
    public AdDto getAdByPid(Long id) {
        log.debug("Retrieving ad by id {}", id);
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Ad not found with id " + id));
        log.debug("Retrieved ad {}", ad);
        AdDto adDto = adMapper.toDto(ad);
        log.info("Returning retrieved ad DTO {}", adDto);
        return adDto;
    }

    public AdServiceImpl(@Value("${app.ads.per-page}") int adsPerPage, AdRepository adRepository,
                         CategoryRepository categoryRepository, AdMapper adMapper, SlugUtils slugUtils,
                         ObjectIdBase62 objectIdBase62) {
        this.ADS_PER_PAGE = adsPerPage;
        this.adRepository = adRepository;
        this.categoryRepository = categoryRepository;
        this.adMapper = adMapper;
        this.slugUtils = slugUtils;
    }

    @Override
    public List<AdDto> getActiveAdsByPage(int page) {
        Page<Ad> adsPage = adRepository.getAdsByStatus(AdStatus.ACTIVE, PageRequest.of(page, ADS_PER_PAGE));
        return adsPage.stream()
                .map(adMapper::toDto)
                .toList();
    }

    @Override
    public AdDto createAd(AdRequest adRequest) {
        log.info("AdService::createAd execution has been started.");
        log.debug("AdService::createAd request parameters {}", adRequest);

        Ad ad = adMapper.toEntity(adRequest);

        ad.setStatus(AdStatus.ACTIVE);                              // todo turn back to status PENDING
        ad.setTitle(StringUtils.clearExtraSpaces(ad.getTitle()));
        ad.setSlug(slugUtils.generateSlug(ad.getTitle()));
        ad.setCreatedAt(LocalDateTime.now());

        // checking if every category exists
        List<Category> adCategories = new ArrayList<>();
        for(Long categoryId : adRequest.getCategoryIds()) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if(optionalCategory.isPresent()) {
                adCategories.add(optionalCategory.get());
            } else {
                throw new EntityNotFoundException("Category with ID: " + categoryId + " does not exist.");
            }
        }
        // set category ids if all of them exist
        ad.setCategories(adCategories);

        Ad savedAd = adRepository.save(ad);
        adRepository.save(savedAd);

        AdDto adDto = adMapper.toDto(savedAd);
        log.debug("AdService::createAd received response from Database {}", savedAd);
        log.info("AdService::createAd execution has been ended.");
        return adDto;
    }

    @Override
    public AdDto updateAd(Long id, AdRequest adRequest) {
        log.info("AdService::updateAd execution has started.");
        log.debug("AdService::updateAd request parameters {}", adRequest);

        if (!adRepository.existsById(id)) {
            log.error("AdService::updateAd: Unable to update nonexistent entity with ID: {}", id);
            throw new EntityNotFoundException("Unable to update nonexistent entity with ID: " + id);
        }

        Ad updatedAd = adMapper.toEntity(adRequest);

        updatedAd.setId(id);
        updatedAd.setTitle(StringUtils.clearExtraSpaces(updatedAd.getTitle()));
        updatedAd.setStatus(AdStatus.PENDING);

        Ad savedAd = adRepository.save(updatedAd);
        AdDto savedAdDto = adMapper.toDto(savedAd);

        log.info("AdService::updateAd: ad with id {} has been updated successfully: {}", id, savedAdDto);
        log.info("AdService::updateAd execution has ended.");
        return savedAdDto;
    }

    @Override
    public ResponseEntity<String> activateAd(Long id) {
        Optional<Ad> adOptional = adRepository.findById(id);
        if (adOptional.isEmpty()) {
            throw new EntityNotFoundException("Unable to activate nonexistent Ad entity with ID: " + id);
        }

        Ad adToActivate = adOptional.get();

        if(adToActivate.getStatus().equals(AdStatus.ACTIVE)) {
            // if ad status is already ACTIVE
            String message = String.format("Ad [ID: %s] is already activated.", adToActivate.getId());
            return new ResponseEntity<>(message, HttpStatus.NOT_MODIFIED);
        } else {
            // if ad status is not ACTIVE
            adToActivate.setStatus(AdStatus.ACTIVE);
            adRepository.save(adToActivate);
            String message = String.format("Ad [ID: %s] was activated successfully.", adToActivate.getId());
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public void deactivateAd(Long id) {
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
    public void archiveAd(Long id) {
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
    public void banAd(Long id) {
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
    public void deleteAd(Long id) {

        if (adRepository.existsById(id)) {
            adRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Unable to delete nonexistent Ad entity with ID: " + id);
        }
    }
}