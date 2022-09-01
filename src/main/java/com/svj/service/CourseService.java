package com.svj.service;

import com.svj.dto.CourseFrequency;
import com.svj.dto.CourseRequestDTO;
import com.svj.dto.CourseResponseDTO;
import com.svj.dto.CourseType;
import com.svj.entity.CourseEntity;
import com.svj.exception.CourseServiceBusinessException;
import com.svj.repository.CourseRepository;
import com.svj.util.AppUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private Logger log;

    CourseService(CourseRepository courseRepository, Logger logger){
        this.courseRepository= courseRepository;
        log= LoggerFactory.getLogger(CourseService.class);
    }


    // create course object in DB
    public CourseResponseDTO onboardCourse(CourseRequestDTO courseRequestDTO){
        CourseEntity courseEntity=AppUtils.mapDTOToEntity(courseRequestDTO);
        courseEntity.setCourseId(new Random().nextInt(500));
        log.info("CourseService::onboardCourse method execution started.");
        try{
            CourseEntity entity= courseRepository.save(courseEntity);
            log.debug("course entity response from Database {}", AppUtils.convertObjectToJson(entity));
            CourseResponseDTO courseResponseDTO= AppUtils.mapEntityToDTO(entity);
            courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
            log.debug("CourseService::onboardCourse method response {}", AppUtils.convertObjectToJson(courseResponseDTO));
            log.info("CourseService::onboardCourse method execution ended.");
            return courseResponseDTO;
        }catch (Exception exception){
            log.error("CourseService::onboardCourse exception occured while persisting the course object to DB- {}", exception.getMessage());
            throw new CourseServiceBusinessException("onboardCourse service method failed: "+exception.getMessage());
        }
    }

    // Load all courses from database
    public List<CourseResponseDTO> viewAllCourses(){
        log.info("CourseService::viewAllCourses method started");
        try{
            Iterable<CourseEntity> courseEntities= courseRepository.findAll();
            List<CourseResponseDTO> response = StreamSupport.stream(courseEntities.spliterator(), false)
                    .map(AppUtils::mapEntityToDTO)
                    .collect(Collectors.toList());
            log.debug("CourseService::viewAllCourses Response from Database: {}", AppUtils.convertObjectToJson(response));
            log.info("CourseService::viewAllCourses Method execution ended");
            return response;
        }catch (Exception exception){
            log.error("CourseService::viewAllCourses Exception occurred while getting response from Database- {}", exception.getMessage());
            throw new CourseServiceBusinessException("viewAllCourses service method failed: "+exception.getMessage());
        }
    }

    // filter course by ID
    public CourseResponseDTO findCourseById(int id){
        log.info("CourseService::findCourseById Starting method");
        try{
            CourseEntity courseEntity=courseRepository.findById(id)
                    .orElseThrow(()->new CourseServiceBusinessException("No matching course found for courseID "+id));
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(courseEntity);
            log.debug("CourseService::findCourseById Response from DB- {}", AppUtils.convertObjectToJson(courseResponseDTO));
            log.info("CourseService::findCourseById Method execution ended");
            return courseResponseDTO;
        }catch (Exception exception){
            log.error("CourseService::findCourseById Error occured while processing method- {}", exception.getMessage());
            throw new CourseServiceBusinessException("findCourseById service method failed: "+exception.getMessage());
        }
    }

    // delete course
    public void deleteCourse(int courseId){
        log.info("CourseService::deleteCourse method exceution started..");
        try{
            log.debug("CourseService::deleteCourse method input {}", courseId);
            courseRepository.deleteById(courseId);
        }catch (Exception exception){
            log.error("CourseService::deleteCourse exception occured while deleting the course object- {}",exception.getMessage());
            throw new CourseServiceBusinessException("deleteCourse service method failed: "+exception.getMessage());
        }
        log.info("CourseService::deleteCourse method exceution ended.");
    }

    // update the course
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO course){
        log.info("CourseService::updateCourse method execution started.");
        try{
            // get existing object
            log.debug("CourseService::updateCourse request payload {} and id {}", AppUtils.convertObjectToJson(course), courseId);
            CourseEntity existingCourseEntity= courseRepository.findById(courseId)
                    .orElseThrow(()-> new CourseServiceBusinessException("course object not present in db with id "+courseId));
            log.debug("CourseService::updateCourse getting existing course object as {}",AppUtils.convertObjectToJson(existingCourseEntity));
            // update properties with new values from requestDTO
            AppUtils.copyDTOToEntity(course, existingCourseEntity);
            CourseEntity courseEntity=courseRepository.save(existingCourseEntity);
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(courseEntity);
            log.debug("CourseService::updateCourse getting updated course object as {}", AppUtils.convertObjectToJson(courseResponseDTO));
            log.info("CourseService::updateCourse method execution ended");
            return courseResponseDTO;
        }catch (Exception exception){
            log.error("CourseService::updateCourse exception occurred while updating course- {}", exception.getMessage());
            throw new CourseServiceBusinessException("updateCourse service method failed: "+exception.getMessage());
        }
    }

    // get frequency of courseType
    // diff in returning array and a list
    public List<CourseFrequency> computeCourseFreq(){
        try{
            Map<CourseType, Long> courseTypeCount = StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                    .collect(Collectors.groupingBy(CourseEntity::getCourseType, Collectors.counting()));
            List<CourseFrequency> result= new LinkedList<>();
            courseTypeCount.forEach((k,v)-> result.add(new CourseFrequency(k, Math.toIntExact(v))));
            return result;
        }catch (Exception exception){
            throw new CourseServiceBusinessException("computeCourseFreq service method failed: "+exception.getMessage());
        }
    }
}
