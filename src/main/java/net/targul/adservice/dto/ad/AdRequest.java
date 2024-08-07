package net.targul.adservice.dto.ad;

import jakarta.validation.constraints.*;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdRequest {

    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 20, max = 100, message = "Tittle length must be from 20 to 100 chars")
    private String title;

    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 20, max = 255, message = "Description length must be from 20 to 255 chars")
    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Image URLs list is mandatory")
    @NotEmpty(message = "Image URLs list cannot be blank")
    @Size(max = 10, message = "Image URLs list cannot contain more than 10 urls")
    private List<String> imageUrls;
}