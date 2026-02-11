package com.sugarfit.assignment.controller;

import com.sugarfit.assignment.constants.UserConstants;
import com.sugarfit.assignment.model.User;
import com.sugarfit.assignment.model.UserRequestDto;
import com.sugarfit.assignment.model.UserResponseDto;
import com.sugarfit.assignment.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Validated
@Log4j2
@Tag(
        name = "API for Save and Get",
        description = "Simple API service which support save and get operation"
)
public class DataProcessController {

    @Autowired
    UserService userService;


    @Operation(
            summary = "API to save the user",
            description = "Call this REST-API to save user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User created success"
    )
    @PostMapping(UserConstants.SAVE_ENDPOINT)
    @RateLimiter(name = "userService", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("User Data received at save Endpoint");
        String uuid = userService.saveUser(userRequestDto);
        log.debug("Saved User:{} successfully by generating UUID:{}", userRequestDto.getUserId(), uuid);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserResponseDto(UserConstants.SUCCESS_STATUS, uuid));
    }


    @Operation(
            summary = "API to Get the user",
            description = "Call this REST-API to get existing user"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User fetch success"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User Not exists"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Server unable to process Request"
            ),
    })
    @GetMapping(UserConstants.GET_ENDPOINT)
    public ResponseEntity<User> getUser(@RequestParam String uuid) {

        log.info("Fetch User request");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(uuid));
    }

    @Operation(
            summary = "API for health",
            description = "Call this REST-API know app status"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User fetch success"
            ),

            @ApiResponse(
                    responseCode = "500",
                    description = "Server Unalienable"
            ),
    })
    @GetMapping(UserConstants.HEALTH_CHECK)
    @RateLimiter(name = "userService", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<Object> healthCheck() {

        log.debug("Health check called");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("status", "UP"));
    }

    // This method runs when the user exceeds the rate limit
    public ResponseEntity<String> rateLimiterFallback(Exception e) {
        log.warn("Rate limit exceeded!");
        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Too many requests - please try again later.");
    }
}
