package com.example.finalproject.service;

import android.content.Context;
import com.example.finalproject.model.Instructor;
import com.example.finalproject.repository.InstructorRepository;

public class InstructorDomainService {

    private final InstructorRepository instructorRepository;

    public InstructorDomainService(Context context) {
        instructorRepository = new InstructorRepository(context);
    }

    public Instructor getInstructor(String name) {
        return instructorRepository.getInstrucorByName(name);
    }

}
