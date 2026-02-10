package com.sugarfit.assignment.model;

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
public class UserRequestDto {

    @NotBlank(message = "user-id must be present")
    @Size(min=4,max=50,message = "user-id length should be between 4 and 50")
    private String userId;

    @NotNull(message = "value is required")
    @Positive(message = "Value must be greater than 0 ")
    private Long value;

}
