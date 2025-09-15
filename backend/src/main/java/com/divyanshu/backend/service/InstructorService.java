package com.divyanshu.backend.service;


import com.divyanshu.backend.model.Instructor;
import com.divyanshu.backend.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;


    public Instructor addInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }
}
