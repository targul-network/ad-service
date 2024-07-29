package net.targul.adservice.mapper;

import net.targul.adservice.dto.ad.AdRequestDto;
import net.targul.adservice.dto.ad.AdDto;
import net.targul.adservice.entity.Ad;
import org.springframework.stereotype.Component;

@Component
public class AdMapper {

    public Ad toEntity(AdRequestDto adRequestDto) {
        if (adRequestDto == null) {
            return null;
        }
        return Ad.builder()
                .title(adRequestDto.getTitle())
                .description(adRequestDto.getDescription())
                .price(adRequestDto.getPrice())
                .build();
    }

    public AdDto toDto(Ad ad) {
        if (ad == null) {
            return null;
        }
        return AdDto.builder()
                .id(ad.getId())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .price(ad.getPrice())
                .build();
    }
}