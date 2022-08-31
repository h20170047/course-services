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
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CourseService {
    private CourseRepository courseRepository;

    // create course object in DB
    public CourseResponseDTO onboardCourse(CourseRequestDTO courseRequestDTO){
        CourseEntity courseEntity=AppUtils.mapDTOToEntity(courseRequestDTO);
        courseEntity.setCourseId(new Random().nextInt(500));
        try{
            CourseEntity entity= courseRepository.save(courseEntity);
            CourseResponseDTO courseResponseDTO= AppUtils.mapEntityToDTO(entity);
            courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
            return courseResponseDTO;
        }catch (Exception exception){
            throw new CourseServiceBusinessException("onboardCourse service method failed: "+exception.getMessage());
        }
    }

    // Load all courses from database
    public List<CourseResponseDTO> viewAllCourses(){
        try{
            Iterable<CourseEntity> courseEntities= courseRepository.findAll();
            return StreamSupport.stream(courseEntities.spliterator(),false)
                    .map(AppUtils::mapEntityToDTO)
                    .collect(Collectors.toList());
        }catch (Exception exception){
            throw new CourseServiceBusinessException("viewAllCourses service method failed: "+exception.getMessage());
        }
    }

    // filter course by ID
    public CourseResponseDTO findCourseById(int id){
        try{
            CourseEntity courseEntity=courseRepository.findById(id)
                    .orElseThrow(()->new CourseServiceBusinessException("No matching course found for courseID "+id));
            return AppUtils.mapEntityToDTO(courseEntity);

        }catch (Exception exception){
            throw new CourseServiceBusinessException("findCourseById service method failed: "+exception.getMessage());
        }
    }

    // delete course
    public void deleteCourse(int courseId){
        try{
            courseRepository.deleteById(courseId);
        }catch (Exception exception){
            throw new CourseServiceBusinessException("deleteCourse service method failed: "+exception.getMessage());
        }
    }

    // update the course
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO course){
        try{
            // get existing object
            CourseEntity existingCourseEntity= courseRepository.findById(courseId)
                    .orElse(null);
            // update properties with new values from requestDTO
            AppUtils.copyDTOToEntity(course, existingCourseEntity);
            CourseEntity courseEntity=courseRepository.save(existingCourseEntity);
            return AppUtils.mapEntityToDTO(courseEntity);
        }catch (Exception exception){
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
