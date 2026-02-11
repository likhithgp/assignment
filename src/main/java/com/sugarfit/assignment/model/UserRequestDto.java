package com.sugarfit.assignment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Schema(
        name = "user",
        description = "Add required field"
)
public class UserRequestDto {

    @Schema(
            description = "Alpha numeric between 4 to 50 characters",
            example = "user123"
    )
    @NotBlank(message = "user-id must be present")
    @Size(min = 4, max = 50, message = "user-id length should be between 4 and 50")
    private String userId;

    @Schema(
            description = "Positive Integer",
            example = "800"
    )
    @NotNull(message = "value is required")
    @Positive(message = "Value must be greater than 0 ")
    private Long value;

}
