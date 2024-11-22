package net.targul.adservice.dto.ad;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class AdRequest {

    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 20, max = 255, message = "Title length must be between 20 and 255 characters")
    private String title;

    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 20, max = 500, message = "Description length must be between 20 and 500 characters")
    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Image URLs list is mandatory")
    @NotEmpty(message = "Image URLs list cannot be empty")
    @Size(max = 10, message = "Image URLs list cannot contain more than 10 URLs")
    private List<@NotNull(message = "Image URL cannot be null")
    @URL(message = "Invalid URL format for Image URL") String> imageUrls;

    @NotNull(message = "Category IDs list is mandatory")
//    @NotEmpty(message = "Category IDs list cannot be empty")
    private List<@NotNull(message = "Category ID cannot be null") Long> categoryIds;
}