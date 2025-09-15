package com.divyanshu.backend.controller;


import com.divyanshu.backend.dto.UserDto;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @Autowired
    private UserService userService;




    @PutMapping("/edit/{userId}")
    public UserDto editUser(@PathVariable int userId, @RequestBody User user) {
        return userService.editUser(userId, user);
    }


    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
    }


    @GetMapping("/list")
    public List<User> listUsers() {
        return userService.listUsers();
    }


    @PostMapping("/{userId}/takecourse/{courseId}")
    public User takeCourse(@PathVariable int userId, @PathVariable int courseId) {
        return userService.takeCourse(userId, courseId);
    }


}
