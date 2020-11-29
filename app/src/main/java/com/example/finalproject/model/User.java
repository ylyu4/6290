package com.example.finalproject.model;

import java.util.List;

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

    public static final String KEY_registeredCourses = "registeredCourses";


    private String username;

    private String password;

    private List<String> registeredCourses;
}
