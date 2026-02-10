package com.sugarfit.assignment.model;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class User {

    // Automatically generates a random UUID string upon instantiation
    private String uuId = UUID.randomUUID().toString();

    @Setter
    private String userId;

    @Setter
    private Long value;

}
