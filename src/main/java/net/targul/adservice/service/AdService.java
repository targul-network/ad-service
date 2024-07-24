package net.targul.adservice.service;

import net.targul.adservice.kafka.event.AdCreatedEvent;
import net.targul.adservice.kafka.producer.AdEventProducer;
import net.targul.adservice.model.Ad;
import net.targul.adservice.repository.AdRepository;
import org.springframework.stereotype.Service;
import net.targul.adservice.model.Category;
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
        AdCreatedEvent event = new AdCreatedEvent(
                ad.getId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getPrice(),
                ad.getCategories().stream().map(Category::getName).toList()
        );

        adEventProducer.sendAdCreatedEvent(event);
        return savedAd;
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    public Ad updateAd(String id, Ad ad) {
        ad.setId(id);
        Ad updatedAd = adRepository.save(ad);
        // TODO Implement kafka event creation
        return updatedAd;
    }

    public void deleteAd(String id) {
        adRepository.deleteById(id);
        // TODO Implement kafka event creation
    }
}
