package com.svj.controller;

import com.svj.dto.CourseRequestDTO;
import com.svj.dto.CourseResponseDTO;
import com.svj.dto.ServiceResponse;
import com.svj.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

     public CourseController(CourseService courseService){
        this.courseService= courseService;
    }

    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@Valid @RequestBody CourseRequestDTO course){
        CourseResponseDTO newCourse= courseService.onboardCourse(course);
        return new ServiceResponse<>(HttpStatus.CREATED, newCourse);
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
        CourseResponseDTO result = null;
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
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody @Valid CourseRequestDTO course){
        CourseResponseDTO courseResponseDTO= courseService.updateCourse(courseId, course);
         return new ServiceResponse<>(HttpStatus.OK, courseResponseDTO);
    }

    @GetMapping("/frequency")
    public ResponseEntity<?> getCurrentFrequency(){
         return new ResponseEntity<>(courseService.computeCourseFreq(), HttpStatus.OK);
    }
}
