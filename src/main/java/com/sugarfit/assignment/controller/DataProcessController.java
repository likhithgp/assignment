package com.sugarfit.assignment.controller;

import com.sugarfit.assignment.model.User;
import com.sugarfit.assignment.model.UserRequestDto;
import com.sugarfit.assignment.model.UserResponseDto;
import com.sugarfit.assignment.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Validated
@Log4j2
public class DataProcessController {

    @Autowired
    UserService userService;


    @PostMapping("/save")
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserRequestDto userRequestDto){
        log.info("User Data received at save Endpoint");
        String uuid = userService.saveUser(userRequestDto);
        log.debug("Saved User:{} sucessfully by genearting UUID:{}",userRequestDto.getUserId(),uuid);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserResponseDto("Success",uuid));
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam String uuid){

        log.info("Fetch User request");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(uuid));
    }
}
