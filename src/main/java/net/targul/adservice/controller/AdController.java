package net.targul.adservice.controller;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.service.AdService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
@Validated
public class AdController {

    private static final Logger log = LoggerFactory.getLogger(AdController.class);
    private final AdService adService;
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping("/{slug}-{shortId}")
    public ResponseEntity<AdDto> getAdBySlugAndShortId(@PathVariable String slug, @PathVariable String shortId) {
        AdDto adDto = adService.getAdBySlugAndShortId(slug, shortId);
        return new ResponseEntity<>(adDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AdDto>> getAllAdsByPage(@RequestParam(value = "p", defaultValue = "0") int page) {

        if(page > 0) {
            page--;
        }

        List<AdDto> adDtoList = adService.getActiveAdsByPage(page);
        return new ResponseEntity<>(adDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdDto> createAd(@RequestBody @Valid AdRequest adRequest) {
        log.info("AdController::createAd request body {}", adRequest);
        AdDto adDto = adService.createAd(adRequest);

        log.info("AdController::createNewProduct response {}", adDto);
        return new ResponseEntity<>(adDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdDto> updateAd(@PathVariable String id, @RequestBody @Valid AdRequest adRequest) {
        log.info("AdController::updateAd id: {}", id);
        log.debug("AdController::updateAd request body {}", adRequest);

        AdDto updatedAdDto = adService.updateAd(id, adRequest);

        log.info("AdController::updateAd response {}", updatedAdDto);
        return new ResponseEntity<>(updatedAdDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateAd(@PathVariable String id) {
        adService.activateAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateAd(@PathVariable String id) {
        adService.deactivateAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<Void> archiveAd(@PathVariable String id) {
        log.info("AdController::archiveAd is processing Ad with id: {}", id);
        adService.archiveAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/ban")
    public ResponseEntity<Void> banAd(@PathVariable String id) {
        log.info("AdController::banAd is processing Ad with id: {}", id);
        adService.banAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable String id) {
        log.info("AdController::banAd is processing Ad with id: {}", id);
        adService.deleteAd(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}