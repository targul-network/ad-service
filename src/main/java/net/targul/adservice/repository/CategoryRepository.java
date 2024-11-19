package net.targul.adservice.repository;

import net.targul.adservice.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
    public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsBySlug(String slug);
    List<Category> findAllByParentCategoryId(UUID parentCategoryId);
    List<Category> findAllByParentCategoryIsNull();
}