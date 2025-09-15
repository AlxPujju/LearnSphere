//package com.divyanshu.backend.config;
//
//import com.divyanshu.backend.filter.JwtFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//
//
//                        .requestMatchers("/api/v1/auth/**").permitAll()
//
//
//                        .requestMatchers("/api/v1/user/**").hasRole("ADMIN")
//
//
//                        .requestMatchers(
//                                "/api/v1/course/add",
//                                "/api/v1/course/edit/**",
//                                "/api/v1/course/delete/**",
//                                "/api/v1/course/assign/**"
//                        ).hasAnyRole("ADMIN", "INSTRUCTOR")
//
//
//                        .requestMatchers(
//                                "/api/v1/course/list",
//                                "/api/v1/course/{courseId}"
//                        ).authenticated()
//
//
//                        .requestMatchers(
//                                "/api/v1/document/add",
//                                "/api/v1/document/edit/**",
//                                "/api/v1/document/delete/**"
//                        ).hasAnyRole("ADMIN", "INSTRUCTOR")
//
//
//                        .requestMatchers(
//                                "/api/v1/document/list",
//                                "/api/v1/document/{documentId}",
//                                "/api/v1/document/download/**"
//                        ).authenticated()
//
//
//                        .requestMatchers("/api/v1/log/**").hasRole("ADMIN")
//
//
//                        .requestMatchers("/api/v1/rate-limit/check/**").authenticated()
//
//
//                        .requestMatchers(
//
//
//                                "/swagger-ui.html",
//
//
//                                "/swagger-ui/**",
//
//
//                                "/v3/api-docs/**",
//
//
//                                "/swagger-resources/**",
//
//
//                                "/webjars/**"
//
//
//                        ).permitAll()
//
//
//                        .anyRequest().authenticated()
//
//
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//        return http.build();
//    }
//
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception {
//        return config.getAuthenticationManager();
//    }
//
//
//}
//

package com.divyanshu.backend.config;

import com.divyanshu.backend.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // Allow H2 console frames
                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // H2 console access

                        // Role-based endpoints
                        .requestMatchers("/api/v1/user/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/api/v1/course/add",
                                "/api/v1/course/edit/**",
                                "/api/v1/course/delete/**",
                                "/api/v1/course/assign/**"
                        ).hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(
                                "/api/v1/course/list",
                                "/api/v1/course/{courseId}"
                        ).authenticated()
                        .requestMatchers(
                                "/api/v1/document/add",
                                "/api/v1/document/edit/**",
                                "/api/v1/document/delete/**"
                        ).hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(
                                "/api/v1/document/list",
                                "/api/v1/document/{documentId}",
                                "/api/v1/document/download/**"
                        ).authenticated()
                        .requestMatchers("/api/v1/log/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/rate-limit/check/**").authenticated()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

