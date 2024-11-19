package net.targul.adservice.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Data
@Builder
public class CategoryRequest {

    @NotNull(message = "Category name is mandatory")
    @Size(min = 1, max = 100, message = "Category name length must be from 1 to 100 chars")
    private String name;

    private UUID parentCategoryId;

    @URL(message = "Invalid Category ImageURL")
    private String imageUrl;
}