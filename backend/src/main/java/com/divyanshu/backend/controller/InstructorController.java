package com.divyanshu.backend.controller;


import com.divyanshu.backend.Exception.NotFoundException;
import com.divyanshu.backend.model.Instructor;
import com.divyanshu.backend.model.Role;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.repository.InstructorRepository;
import com.divyanshu.backend.repository.UserRepository;
import com.divyanshu.backend.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class InstructorController {


    @Autowired
    InstructorService instructorService;


    @Autowired
    UserRepository userRepository;


    @PostMapping(value = "/joinAsInstructor/{userId}")
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor instructor, @PathVariable int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setRole(Role.INSTRUCTOR);
        userRepository.save(user);
        instructor.setUser(user);
        Instructor instructor1 = instructorService.addInstructor(instructor);
        return ResponseEntity.ok(instructor1);
    }


}
