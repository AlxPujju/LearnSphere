package com.divyanshu.backend.service;


import com.divyanshu.backend.model.Metrics;
import com.divyanshu.backend.repository.CourseRepository;
import com.divyanshu.backend.repository.DocumentRepository;
import com.divyanshu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DashboardService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private DocumentRepository documentRepository;


    public Metrics getMetrics() {
        long userCount = userRepository.count();
        long courseCount = courseRepository.count();
        long documentCount = documentRepository.count();


        return new Metrics(userCount, courseCount, documentCount);
    }
}
