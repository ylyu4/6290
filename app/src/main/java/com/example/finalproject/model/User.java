package com.example.finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public static final String TABLE = "User";

    public static final String KEY_username = "username";

    public static final String KEY_password = "password";


    private String username;

    private String password;
}
