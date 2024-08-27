package net.targul.adservice.repository;

import net.targul.adservice.entity.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    boolean existsBySlug(String slug);
    List<Category> findAllByParentCategoryId(String parentCategoryId);
    List<Category> findAllByParentCategoryIsNull();
}