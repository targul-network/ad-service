package net.targul.adservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "specifications")
public class Specification {
    @Id
    private String id;
    private String name;
    private List<SpecificationDetail> specificationDetails;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecificationDetail> getSpecificationDetails() {
        return specificationDetails;
    }

    public void setSpecificationDetails(List<SpecificationDetail> specificationDetails) {
        this.specificationDetails = specificationDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
