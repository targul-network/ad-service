package net.targul.adservice.dto.ad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

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
}