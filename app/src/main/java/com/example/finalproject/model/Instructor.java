package com.example.finalproject.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

    public static final String TABLE = "Instructor";

    public static final String KEY_name = "name";

    public static final String KEY_phoneNumber = "phoneNumber";

    public static final String KEY_email = "email";

    public static final String KEY_office = "office";

    public static final String KEY_rateMyProfessorId = "rateMyProfessorId";

    private String name;

    private String phoneNumber;

    private String email;

    private String office;

    private String rateMyProfessorId;
}
