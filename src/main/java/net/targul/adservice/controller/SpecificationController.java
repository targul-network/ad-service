package net.targul.adservice.controller;

import net.targul.adservice.model.Specification;
import net.targul.adservice.service.SpecificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specs")
public class SpecificationController {
    private final SpecificationService specificationService;

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }

    @GetMapping
    public ResponseEntity<List<Specification>> getAllSpecifications() {
        return ResponseEntity.ok().body(specificationService.getAllSpecifications());
    }
}
