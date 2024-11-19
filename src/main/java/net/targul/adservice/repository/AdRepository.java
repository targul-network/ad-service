package net.targul.adservice.repository;

import net.targul.adservice.domain.ad.Ad;
import net.targul.adservice.domain.ad.AdStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdRepository extends MongoRepository<Ad, String> {

    Optional<Ad> getAdByShortId(String shortId);

    Page<Ad> getAdsByStatus(AdStatus status, Pageable pageable);
}