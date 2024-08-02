package net.targul.adservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Builder
@Getter
@Setter
@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String name;
    private String parentId;
    private ArrayList<String> featureIds;
}