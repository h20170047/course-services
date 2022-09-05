package com.svj.controller;

import com.svj.dto.*;
import com.svj.service.CourseService;
import com.svj.util.AppUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Add a new course to system")
    @ApiResponses(value={
            @ApiResponse(responseCode ="201", description="course added successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CourseResponseDTO.class))
            }),
            @ApiResponse(responseCode ="400", description="validation error")
    })
    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@Valid @RequestBody CourseRequestDTO course){
         log.info("CourseController:addCourse Request payload: {}", AppUtils.convertObjectToJson(course));
        CourseResponseDTO newCourse= courseService.onboardCourse(course);
        ServiceResponse<CourseResponseDTO> serviceResponse= new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.CREATED);
        serviceResponse.setResponse(newCourse);
        log.info("CourseController:addCourse Response: {}", AppUtils.convertObjectToJson(serviceResponse));
        return serviceResponse;
    }

    @GetMapping
    @Operation(summary = "Fetch all courses")
    public ServiceResponse<?> findAllCourses(){
         log.info("CourseController:findAllCourses starting finaAllCourses method");
        ServiceResponse serviceResponse= new ServiceResponse();
        serviceResponse.setStatus(HttpStatus.OK);
        List<CourseResponseDTO> courseResponseDTOS = courseService.viewAllCourses();
        serviceResponse.setResponse(courseResponseDTOS);
        log.info("CourseController:findAllCourses Response- {}", AppUtils.convertObjectToJson(serviceResponse));
         return serviceResponse;
    }

    @Operation(summary = "Find course by courseId")
    @ApiResponses(value={
            @ApiResponse(responseCode ="200", description="course found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CourseResponseDTO.class))
            }),
            @ApiResponse(responseCode ="400", description="course not found with given ID")
    })
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


    @Operation(summary = "Fetch course by given id")
    @GetMapping("/search")
    public ServiceResponse<?> findCourseUsingReqParam(@RequestParam(required = false) Optional<Integer> courseId){
         log.warn("CourseController:findCourseUsingReqParam is a depricated method, please use findCourse");
        CourseResponseDTO result = null;
        ServiceResponse<CourseResponseDTO> serviceResponse= new ServiceResponse<>();
        if(courseId.isPresent())
             result= courseService.findCourseById(courseId.get());
        serviceResponse.setResponse(result);
        serviceResponse.setStatus(HttpStatus.OK);
        return serviceResponse;
    }

    @Operation(summary = "Delete course by courseID")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable  Integer courseId){
         log.info("CourseController:deleteCourse deleting course with id {}", courseId);
         courseService.deleteCourse(courseId);
         return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "update the  course in system")
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

    @Operation(summary = "Get count of different types of courses")
    @GetMapping("/frequency")
    public ServiceResponse<?> getCurrentFrequency(){
         return new ServiceResponse<List<CourseFrequency>>(HttpStatus.OK, courseService.computeCourseFreq());
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
