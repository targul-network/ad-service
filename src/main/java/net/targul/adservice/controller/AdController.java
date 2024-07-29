package net.targul.adservice.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.targul.adservice.dto.ad.AdRequestDto;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.service.AdService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
@Slf4j
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @PostMapping
    public ResponseEntity<AdDto> createAd(@RequestBody @Valid AdRequestDto adRequestDto) {
        log.info("AdController::createAd request body {}", adRequestDto);
        AdDto adDto = adService.createAd(adRequestDto);

        log.info("ProductController::createNewProduct response {}", adDto);
        return new ResponseEntity<>(adDto, HttpStatus.CREATED);
    }
}