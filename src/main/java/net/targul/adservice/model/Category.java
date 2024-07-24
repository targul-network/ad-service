package net.targul.adservice.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String name;
    private String imageUrl;
    private Category parent;
    private List<Specification> specificationDetails;

    // Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Specification> getSpecificationDetails() {
        return specificationDetails;
    }

    public void setSpecificationDetails(List<Specification> specificationDetails) {
        this.specificationDetails = specificationDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
