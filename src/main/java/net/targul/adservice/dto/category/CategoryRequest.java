package net.targul.adservice.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {

    @NotNull(message = "Category name is mandatory")
    @Size(min = 1, max = 100, message = "Category name length must be from 1 to 100 chars")
    private String name;

    @Size(min = 24, max = 24, message = "Parent category id length must be 24")
    private String parentCategoryId;
}