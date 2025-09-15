package com.divyanshu.backend.service;


import com.divyanshu.backend.dto.UserDto;
import com.divyanshu.backend.model.Role;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {


    @Autowired
    UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private ModelMapper modelMapper;


    public UserDto register(User user) {
        if(user.getRole() == null){
            user.setRole(Role.USER);
        }
        user.setPswrd(passwordEncoder.encode(user.getPswrd()));
        userRepository.save(user);
        return modelMapper.map(user , UserDto.class);
    }
}
