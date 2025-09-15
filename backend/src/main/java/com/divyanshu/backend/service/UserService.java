package com.divyanshu.backend.service;


import com.divyanshu.backend.Exception.NotFoundException;
import com.divyanshu.backend.dto.UserDto;
import com.divyanshu.backend.model.Course;
import com.divyanshu.backend.model.Module;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.repository.CourseRepository;
import com.divyanshu.backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByName(userName);
        if(user == null) {
            throw  new UsernameNotFoundException("User not found with email: " + userName);
        }
        return  new UserDetailsImpl(user);
    }


    @Autowired
    private CourseRepository courseRepository;


    @Autowired
    private ModelMapper modelMapper;


    public UserDto editUser(int userId, User updatedUser) {
        User  queryUser = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
        queryUser.setName(updatedUser.getName());
        return modelMapper.map(userRepository.save(queryUser),UserDto.class);
    }


    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }


    public List<User> listUsers() {
        return userRepository.findAll();
    }


    public User takeCourse(int userId, int courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));
        course.setUser(user);
        return userRepository.save(user);
    }
}
