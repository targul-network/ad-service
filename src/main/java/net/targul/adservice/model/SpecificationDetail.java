package net.targul.adservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "specificationDetails")
@Setter
@Getter
public class SpecificationDetail {
    private String name;
    private String value;
}