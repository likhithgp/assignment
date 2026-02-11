package com.sugarfit.assignment.model;


import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


@Getter
public class User {

    // Automatically generates a random UUID string upon instantiation
    private String uuId;

    @Setter
    private String userId;

    @Setter
    private Long value;

    public User(String userId, Long value) {
        this.userId = userId;
        this.value = value;
        String source = userId + value;
        this.uuId = UUID.nameUUIDFromBytes(source.getBytes(StandardCharsets.UTF_8)).toString();
    }

}
