package com.svj.controller;

import com.svj.dto.CourseFrequency;
import com.svj.dto.CourseRequestDTO;
import com.svj.dto.CourseResponseDTO;
import com.svj.dto.ServiceResponse;
import com.svj.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

     public CourseController(CourseService courseService){
        this.courseService= courseService;
    }

    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody CourseRequestDTO course){
        CourseResponseDTO newCourse= courseService.onboardCourse(course);
        return new ServiceResponse<>(HttpStatus.CREATED, newCourse);
    }

    @GetMapping
    public ServiceResponse<List<CourseResponseDTO>> findAllCourses(){
         List<CourseResponseDTO> courseResponseDTOS= courseService.viewAllCourses();
         return new ServiceResponse<>(HttpStatus.OK, courseResponseDTOS);
    }

    @GetMapping("/search/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourse(@PathVariable Integer courseId){
         CourseResponseDTO courseResponseDTO= courseService.findCourseById(courseId);
         return new ServiceResponse<>(HttpStatus.OK, courseResponseDTO);
    }

    @GetMapping("/search")
    public ServiceResponse<CourseResponseDTO> findCourseUsingReqParam(@RequestParam(required = false) Optional<Integer> courseId){
        CourseResponseDTO result = null;
        if(courseId.isPresent())
             result= courseService.findCourseById(courseId.get());
        return new ServiceResponse<>(HttpStatus.OK, result);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable  Integer courseId){
         courseService.deleteCourse(courseId);
         return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody CourseRequestDTO course){
        CourseResponseDTO courseResponseDTO= courseService.updateCourse(courseId, course);
         return new ServiceResponse<>(HttpStatus.OK, courseResponseDTO);
    }

    @GetMapping("/frequency")
    public ServiceResponse<List<CourseFrequency>> getCurrentFrequency(){
         courseService.computeCourseFreq();
        List<CourseFrequency> freqList= courseService.computeCourseFreq();
         return new ServiceResponse<>(HttpStatus.OK, freqList);
    }
}
