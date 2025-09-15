package com.divyanshu.backend.controller;
import com.divyanshu.backend.dto.UserDto;
import com.divyanshu.backend.model.Log;
import com.divyanshu.backend.model.Report;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.service.AuthService;
import com.divyanshu.backend.service.JwtService;
import com.divyanshu.backend.service.LogService;
import com.divyanshu.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.divyanshu.backend.service.UserService;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private LogService logService;
    @Autowired
    private ReportService reportService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user){
        Log log = new Log();


        log.setContent(user.toString());
        log.setAction("User Registered :");
        log.setUser(user);


        UserDto user1 = authService.register(user);
        if(user1 != null){
            Log log2 = logService.saveLog(log);


            Report report = new Report();
            report.setContent("Registration happened");
            report.setLog(log2);




            reportService.saveReport(report);
        }
        return  ResponseEntity.ok(user1);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getName(), user.getPswrd()
                    )
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());
            System.out.println("Details " + userDetails);
            String jwt = jwtService.generteToken(userDetails);
            System.out.println("Jwt " + jwt);
            return ResponseEntity.ok(Collections.singletonMap("token", jwt));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
