package net.targul.adservice.repository;

import net.targul.adservice.model.Specification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationRepository extends MongoRepository<Specification, String> {
}
