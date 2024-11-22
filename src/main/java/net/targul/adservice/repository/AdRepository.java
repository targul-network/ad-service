package net.targul.adservice.repository;

import java.util.UUID;
import net.targul.adservice.domain.ad.Ad;
import net.targul.adservice.domain.ad.AdStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, UUID> {
    Page<Ad> getAdsByStatus(AdStatus status, Pageable pageable);
}