package net.targul.adservice.service.impl;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.domain.ad.Ad;
import net.targul.adservice.domain.ad.AdStatus;
import net.targul.adservice.domain.Category;
import net.targul.adservice.service.exception.EntityNotFoundException;
import net.targul.adservice.service.exception.ad.AdStatusException;
import net.targul.adservice.mapper.AdMapper;
import net.targul.adservice.repository.AdRepository;
import net.targul.adservice.repository.CategoryRepository;
import net.targul.adservice.service.AdService;

import net.targul.adservice.util.id.base62.impl.ObjectIdBase62;
import net.targul.adservice.util.SlugUtils;
import net.targul.adservice.util.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AdServiceImpl implements AdService {

    private static final Logger log = LoggerFactory.getLogger(AdServiceImpl.class);
    private final int ADS_PER_PAGE;
    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final AdMapper adMapper;
    private final SlugUtils slugUtils;
    private final ObjectIdBase62 objectIdBase62;

    public AdServiceImpl(@Value("${app.ads.per-page}") int adsPerPage, AdRepository adRepository,
                         CategoryRepository categoryRepository, AdMapper adMapper, SlugUtils slugUtils,
                         ObjectIdBase62 objectIdBase62) {
        ADS_PER_PAGE = adsPerPage;
        this.adRepository = adRepository;
        this.categoryRepository = categoryRepository;
        this.adMapper = adMapper;
        this.slugUtils = slugUtils;
        this.objectIdBase62 = objectIdBase62;
    }

    @Override
    public ResponseEntity<AdDto> getAdBySlugAndShortId(String slug, String shortId) {
        // getting ad from repository by shortId
        Optional<Ad> optionalAd = adRepository.getAdByShortId(shortId);

        // checking if ad was found
        if (optionalAd.isEmpty()) {
            throw new EntityNotFoundException("Unable to find Ad entity with Short ID: " + shortId);
        }

        Ad ad = optionalAd.get();

        // checking if ad has accepted status to be shown
        AdStatus adStatus = ad.getStatus();
        if(adStatus.equals(AdStatus.PENDING) || adStatus.equals(AdStatus.BANNED)) {
            throw new AdStatusException("Access to this Ad is currently forbidden. Ad status: " + adStatus);
        }

        // TODO check if the found ad by short id has the same slug as in url
//        if (!slug.equals(ad.getSlug())) {
//            String correctUrl = String.format("ads/%s-%s", ad.getSlug(), shortId);
//            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", correctUrl).build();
//        }

        return new ResponseEntity<>(adMapper.toDto(ad), HttpStatus.OK);
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

        ad.setStatus(AdStatus.PENDING);
        ad.setTitle(StringUtils.clearExtraSpaces(ad.getTitle()));
        ad.setSlug(slugUtils.generateSlug(ad.getTitle()));
        ad.setCreatedAt(LocalDateTime.now());

        // checking if every category exists
        List<Category> adCategories = new ArrayList<>();
        for(String categoryId : adRequest.getCategoryIds()) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if(optionalCategory.isPresent()) {
                adCategories.add(optionalCategory.get());
            } else {
                throw new EntityNotFoundException("Category with ID: " + categoryId + " does not exist.");
            }
        }
        // set category ids if all of them exist
        ad.setCategoryIds(adCategories);

        Ad savedAd = adRepository.save(ad);
        savedAd.setShortId(objectIdBase62.encode(new ObjectId(savedAd.getId())));
        adRepository.save(savedAd);

        AdDto adDto = adMapper.toDto(savedAd);
        log.debug("AdService::createAd received response from Database {}", savedAd);
        log.info("AdService::createAd execution has been ended.");
        return adDto;
    }

    @Override
    public AdDto updateAd(String id, AdRequest adRequest) {
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
    public ResponseEntity<String> activateAd(String id) {
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