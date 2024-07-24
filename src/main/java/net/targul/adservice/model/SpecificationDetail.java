package net.targul.adservice.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "specificationDetails")
public class SpecificationDetail {
    private String name;
    private String value;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
