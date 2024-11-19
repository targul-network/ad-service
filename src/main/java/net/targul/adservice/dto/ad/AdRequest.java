package net.targul.adservice.dto.ad;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class AdRequest {

    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 20, max = 255, message = "Tittle length must be from 20 to 150 chars")
    private String title;

    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 20, max = 500, message = "Description length must be from 20 to 500 chars")
    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Image URLs list is mandatory")
    @NotEmpty(message = "Image URLs list cannot be blank")
    @Size(max = 10, message = "Image URLs list cannot contain more than 10 urls")
    private List<@NotNull(message = "Value for Image URL cannot be null")
                 @URL(message = "Invalid value for Image URL") String> imageUrls;

    @NotNull
    @NotEmpty
    @Size(min = 1, message = "Ad must be linked to at least one category")
    private List<UUID> categoryIds;
}