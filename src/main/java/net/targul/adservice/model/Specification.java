package net.targul.adservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "specifications")
@Getter
@Setter
public class Specification {
    @Id
    private String id;
    private String name;
    private List<SpecificationDetail> specificationDetails;
}