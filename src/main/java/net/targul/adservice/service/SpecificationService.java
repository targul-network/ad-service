package net.targul.adservice.service;

import net.targul.adservice.model.Specification;
import net.targul.adservice.repository.SpecificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationService {
    private final SpecificationRepository specificationRepository;

    public SpecificationService(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    public List<Specification> getAllSpecifications() {
        return specificationRepository.findAll();
    }
}
