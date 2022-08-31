package com.svj.controller;

import com.svj.dto.Course;
import com.svj.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

     public CourseController(CourseService courseService){
        this.courseService= courseService;
    }

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course){
        Course newCourse= courseService.onboardCourse(course);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAllCourses(){
         return new ResponseEntity<>(courseService.viewAllCourses(),HttpStatus.OK);
    }

    @GetMapping("/search/{courseId}")
    public ResponseEntity<?> findCourse(@PathVariable Integer courseId){
         return new ResponseEntity<>(courseService.findCourseById(courseId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findCourseUsingReqParam(@RequestParam(required = false) Optional<Integer> courseId){
        Course result = null;
        if(courseId.isPresent())
             result= courseService.findCourseById(courseId.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable  Integer courseId){
         courseService.deleteCourse(courseId);
         return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable int courseId, @RequestBody Course course){
         return new ResponseEntity<>(courseService.updateCourse(courseId, course), HttpStatus.OK);
    }

    @GetMapping("/frequency")
    public ResponseEntity<?> getCurrentFrequency(){
         return new ResponseEntity<>(courseService.computeCourseFreq(), HttpStatus.OK);
    }
}
