package net.targul.adservice.mapper;

import net.targul.adservice.dto.ad.AdRequest;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.entity.ad.Ad;

import org.springframework.stereotype.Component;

@Component
public class AdMapper {

    public Ad toEntity(AdRequest adRequest) {
        if (adRequest == null) {
            return null;
        }
        return Ad.builder()
                .title(adRequest.getTitle())
                .description(adRequest.getDescription())
                .price(adRequest.getPrice())
                .imageUrls(adRequest.getImageUrls())
                .build();
    }

    public AdDto toDto(Ad ad) {
        if (ad == null) {
            return null;
        }
        return AdDto.builder()
                .shortId(ad.getShortId())
                .status(String.valueOf(ad.getStatus()))
                .title(ad.getTitle())
                .slug(ad.getSlug())
                .description(ad.getDescription())
                .price(ad.getPrice())
                .imageUrls(ad.getImageUrls())
                .createdAt(ad.getCreatedAt())
                .build();
    }
}