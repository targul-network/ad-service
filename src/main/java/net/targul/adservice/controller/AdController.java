package net.targul.adservice.controller;

import net.targul.adservice.model.Ad;
import net.targul.adservice.service.AdService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {
    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping
    public Ad createAd(@RequestBody Ad ad) {
        return adService.createAd(ad);
    }

    @GetMapping
    public List<Ad> getAllAds() {
        return adService.getAllAds();
    }

    @PutMapping("/{id}")
    public Ad updateAd(@PathVariable String id, @RequestBody Ad ad) {
        return adService.updateAd(id, ad);
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable String id) {
        adService.deleteAd(id);
    }
}
