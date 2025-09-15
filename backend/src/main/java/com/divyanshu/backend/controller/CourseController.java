package com.divyanshu.backend.controller;

import com.divyanshu.backend.dto.CourseDto;
import com.divyanshu.backend.model.Course;
import com.divyanshu.backend.model.Log;
import com.divyanshu.backend.model.Report;
import com.divyanshu.backend.model.User;
import com.divyanshu.backend.service.AuthenticatedUserService;
import com.divyanshu.backend.service.CourseService;
import com.divyanshu.backend.service.LogService;
import com.divyanshu.backend.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
    @Autowired
    private CourseService courseService;


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private LogService logService;


    @Autowired
    private AuthenticatedUserService authenticatedUserService;


    @Autowired
    private ReportService reportService;


    @PostMapping("/add/{instructorId}")
    private ResponseEntity<Course> addCourse(@RequestBody Course course , @PathVariable int instructorId){
        Course resCourse = courseService.addCourse(course , instructorId);
        return ResponseEntity.ok(resCourse);
    }
    @PutMapping("/edit/{courseId}")
    private ResponseEntity<CourseDto> editCourse(@RequestBody Course course , @PathVariable int courseId){
        CourseDto resCourse = courseService.editCourse(courseId,course);
        return ResponseEntity.ok(resCourse);
    }
    @DeleteMapping("/delete/{courseId}")
    private ResponseEntity<String> deleteCourse(){
        User tempUser = authenticatedUserService.getAuthenticatedUser();


        Log log = new Log();
        log.setContent(log.toString());
        log.setAction("Course Deleted :");
        log.setUser(tempUser);


        if(tempUser != null){
            Log log2 = logService.saveLog(log);


            Report report = new Report();
            report.setContent("Course was Deleted");
            report.setLog(log2);


            reportService.saveReport(report);
        }


        return ResponseEntity.ok("Deleted");
    }
    @GetMapping("/list")
    public ResponseEntity<Page<Course>> courses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(courseService.getCourses(page, size, sortBy));
    }


    @GetMapping("/{courseId}")
    private ResponseEntity<Course> course(@PathVariable int courseId){
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }
    @PostMapping("/assign/{courseId}/user/{userId}")
    private ResponseEntity<CourseDto> assignCourse(@PathVariable int courseId, @PathVariable int userId){
        return ResponseEntity.ok(courseService.assignCourse(courseId,userId));
    }


}
