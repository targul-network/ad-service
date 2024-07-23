package net.targul.adservice.service;

import net.targul.adservice.event.AdEvent;
import net.targul.adservice.kafka.AdEventProducer;
import net.targul.adservice.model.Ad;
import net.targul.adservice.repository.AdRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    private final AdRepository adRepository;
    private final AdEventProducer adEventProducer;

    public AdService(AdRepository adRepository, AdEventProducer adEventProducer) {
        this.adRepository = adRepository;
        this.adEventProducer = adEventProducer;
    }

    public Ad createAd(Ad ad) {
        Ad savedAd = adRepository.save(ad);
        adEventProducer.sendAdEvent(new AdEvent("AdCreated", savedAd));
        return savedAd;
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    public Ad updateAd(String id, Ad ad) {
        ad.setId(id);
        Ad updatedAd = adRepository.save(ad);
        adEventProducer.sendAdEvent(new AdEvent("AdUpdated", updatedAd));
        return updatedAd;
    }

    public void deleteAd(String id) {
        adRepository.deleteById(id);
        adEventProducer.sendAdEvent(new AdEvent("AdDeleted", new Ad(id)));
    }
}
