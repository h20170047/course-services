package com.svj.controller;

import com.svj.dto.CourseRequestDTO;
import com.svj.dto.CourseResponseDTO;
import com.svj.dto.ServiceResponse;
import com.svj.service.CourseService;
import com.svj.util.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
//@Slf4j
public class CourseController {
    private CourseService courseService;
    private Logger log;

     public CourseController(CourseService courseService, Logger logger){
        this.courseService= courseService;
         log= LoggerFactory.getLogger(CourseController.class);
    }

    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@Valid @RequestBody CourseRequestDTO course){
         log.info("CourseController:addCourse Request payload: {}", AppUtils.convertObjectToJson(course));
        CourseResponseDTO newCourse= courseService.onboardCourse(course);
        ServiceResponse<CourseResponseDTO> serviceResponse= new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.OK);
        serviceResponse.setResponse(newCourse);
        log.info("CourseController:addCourse Response: {}", AppUtils.convertObjectToJson(serviceResponse));
        return serviceResponse;
    }

    @GetMapping
    public ServiceResponse<?> findAllCourses(){
         log.info("CourseController:findAllCourses starting finaAllCourses method");
        ServiceResponse serviceResponse= new ServiceResponse();
        serviceResponse.setStatus(HttpStatus.OK);
        List<CourseResponseDTO> courseResponseDTOS = courseService.viewAllCourses();
        serviceResponse.setResponse(courseResponseDTOS);
        log.info("CourseController:findAllCourses Response- {}", AppUtils.convertObjectToJson(serviceResponse));
         return serviceResponse;
    }

    @GetMapping("/search/{courseId}")
    public ServiceResponse<?> findCourse(@PathVariable Integer courseId){
         log.info("CourseController:findCourse starting method with request- {}",courseId);
        ServiceResponse serviceResponse= new ServiceResponse();
        serviceResponse.setStatus(HttpStatus.OK);
        CourseResponseDTO courseResponseDTO= courseService.findCourseById(courseId);
        serviceResponse.setResponse(courseResponseDTO);
        log.info("CourseController:findCourse ending method with response- {}", AppUtils.convertObjectToJson(courseResponseDTO));
         return serviceResponse;
    }

    @GetMapping("/search")
    public ResponseEntity<?> findCourseUsingReqParam(@RequestParam(required = false) Optional<Integer> courseId){
         log.warn("CourseController:findCourseUsingReqParam is a depricated method, please use findCourse");
        CourseResponseDTO result = null;
        if(courseId.isPresent())
             result= courseService.findCourseById(courseId.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable  Integer courseId){
         log.info("CourseController:deleteCourse deleting course with id {}", courseId);
         courseService.deleteCourse(courseId);
         return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody @Valid CourseRequestDTO course){
        log.info("CourseController:updateCourse Request payload {} and {}", AppUtils.convertObjectToJson(course), courseId);
        CourseResponseDTO courseResponseDTO= courseService.updateCourse(courseId, course);
        ServiceResponse<CourseResponseDTO> serviceResponse= new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.OK);
        serviceResponse.setResponse(courseResponseDTO);
        log.info("CourseController:updateCourse Repsonse body: {}", AppUtils.convertObjectToJson(serviceResponse));
         return serviceResponse;
    }

    @GetMapping("/frequency")
    public ResponseEntity<?> getCurrentFrequency(){
         return new ResponseEntity<>(courseService.computeCourseFreq(), HttpStatus.OK);
    }

    @GetMapping("/log")
    public String loggingLevel(){
        log.trace("trace message");
        log.debug("debug message");
        log.info("info message");
        log.warn("warn message");
        log.error("error message");
        return "log printed in console";
    }
}
