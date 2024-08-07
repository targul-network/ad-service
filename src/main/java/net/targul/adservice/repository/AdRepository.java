package net.targul.adservice.repository;

import net.targul.adservice.entity.ad.Ad;
import net.targul.adservice.entity.ad.AdStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends MongoRepository<Ad, String> {

    Page<Ad> getAdsByStatus(AdStatus status, Pageable pageable);
}