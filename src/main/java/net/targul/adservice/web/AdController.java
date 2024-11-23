package net.targul.adservice.web;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.service.AdService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
@Validated
@Slf4j
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping("/{pid}")
    public ResponseEntity<AdDto> getAdByPid(@PathVariable Long pid) {
        return ResponseEntity.ok(adService.getAdByPid(pid));
    }

    @GetMapping
    public ResponseEntity<List<AdDto>> getAllAdsByPage(@RequestParam(value = "page", defaultValue = "2") int page) {
        List<AdDto> adDtoList = adService.getActiveAdsByPage(--page);
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
    public ResponseEntity<AdDto> updateAd(@PathVariable Long id, @RequestBody @Valid AdRequest adRequest) {
        log.info("AdController::updateAd id: {}", id);
        log.debug("AdController::updateAd request body {}", adRequest);

        AdDto updatedAdDto = adService.updateAd(id, adRequest);

        log.info("AdController::updateAd response {}", updatedAdDto);
        return new ResponseEntity<>(updatedAdDto, HttpStatus.OK);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateAd(@PathVariable Long id) {
        return adService.activateAd(id);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateAd(@PathVariable Long id) {
        adService.deactivateAd(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<Void> archiveAd(@PathVariable Long id) {
        log.info("AdController::archiveAd is processing Ad with id: {}", id);
        adService.archiveAd(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/ban")
    public ResponseEntity<Void> banAd(@PathVariable Long id) {
        log.info("AdController::banAd is processing Ad with id: {}", id);
        adService.banAd(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        log.info("AdController::banAd is processing Ad with id: {}", id);
        adService.deleteAd(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}