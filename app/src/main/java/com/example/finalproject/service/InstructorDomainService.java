package com.example.finalproject.service;

import android.content.Context;
import com.example.finalproject.model.Instructor;
import com.example.finalproject.repository.InstructorRepository;

public class InstructorDomainService {

    private final InstructorRepository instructorRepository;

    public InstructorDomainService(Context context) {
        instructorRepository = new InstructorRepository(context);
    }

    // get an instructor by a instructor name
    public Instructor getInstructor(String name) {
        return instructorRepository.getInstructorByName(name);
    }

}
