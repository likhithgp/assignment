package com.sugarfit.assignment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "response",
        description = "API response"
)
public class UserResponseDto {

    @Schema(
            description = "Operation status",
            example = "success"
    )
    private String status;
    @Schema(
            description = "Status code",
            example = "200"
    )
    private String requestId;
}
