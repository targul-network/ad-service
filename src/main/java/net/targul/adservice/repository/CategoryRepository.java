package net.targul.adservice.repository;

import net.targul.adservice.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsBySlug(String slug);
    List<Category> findAllByParentCategoryId(Long parentCategoryId);
    List<Category> findAllByParentCategoryIsNull();
}