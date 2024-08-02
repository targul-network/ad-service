package net.targul.adservice.dto.ad;

import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdRequest {

    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Image URLs list is mandatory")
    @NotEmpty(message = "Image URLs list cannot be blank")
    @Size(max = 10, message = "Image URLs list cannot contain more than 10 urls")
    private List<String> imageUrls;

    @NotNull(message = "Image categoryIds list is mandatory")
    @NotEmpty(message = "Image categoryIds list cannot be blank")
    private List<String> categoryIds;
}