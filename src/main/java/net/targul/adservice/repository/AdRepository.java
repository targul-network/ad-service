package net.targul.adservice.repository;

import net.targul.adservice.domain.ad.Ad;
import net.targul.adservice.domain.ad.AdStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdRepository extends JpaRepository<Ad, UUID> {
    Optional<Ad> getAdByShortId(String shortId);
    Page<Ad> getAdsByStatus(AdStatus status, Pageable pageable);
}