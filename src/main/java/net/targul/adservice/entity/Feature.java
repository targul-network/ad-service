package net.targul.adservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@Document(collection = "features")
public class Feature {
    @Id
    private String id;
    private String name;
    private List<String> values;
}