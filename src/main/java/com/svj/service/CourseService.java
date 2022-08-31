package com.svj.service;

import com.svj.dto.Course;
import com.svj.dto.CourseFrequency;
import com.svj.dto.CourseType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    private List<Course> courseDatabase= new ArrayList<>();

    // create course object in DB
    public Course onboardCourse(Course course){
        course.setCourseId(new Random().nextInt(500));
        courseDatabase.add(course);
        return course;
    }

    // Load all courses from database
    public List<Course> viewAllCourses(){
        return courseDatabase;
    }

    // filter course by ID
    public Course findCourseById(int id){
        for(Course course: courseDatabase){
            if(course.getCourseId()== id)
                return course;
        }
        return null;
//        return courseDatabase.stream()
//                .filter(course -> course.getCourseId()== id)
//                .findFirst().orElse(null);
    }

    // delete course
    public void deleteCourse(Integer courseId){
        Course course=findCourseById(courseId);
        courseDatabase.remove(course);
    }

    // update the course
    public Course updateCourse(int courseId, Course course){
        Course existingCourse= findCourseById(courseId);
        courseDatabase.set(courseDatabase.indexOf(existingCourse), course);
        return course;
    }

    // get frequency of courseType
    // diff in returning array and a list
    public List<CourseFrequency> computeCourseFreq(){
//        List<CourseFrequency> result= new LinkedList<>();
//        Map<CourseType, CourseFrequency> map= new HashMap<>();
//        for(CourseType courseType: CourseType.values()){
//            CourseFrequency courseFrequency= new CourseFrequency(courseType, 0);
//            result.add(courseFrequency);
//            map.put(courseType, courseFrequency);
//        }
//        for(Course course: courseDatabase){
//            CourseFrequency courseFrequency= map.get(course.getCourseType());
//            courseFrequency.setCount(courseFrequency.getCount()+1);
//            map.put(course.getCourseType(), courseFrequency);
//        }
//        return result;
        Map<CourseType, Integer> map= new HashMap<>();
        courseDatabase.stream()
                .forEach(course -> {
                    map.put(course.getCourseType(),map.getOrDefault(course.getCourseType(), 0)+1);
                });
        List<CourseFrequency> result= new LinkedList<>();
        for(CourseType courseType : map.keySet()){
            result.add(new CourseFrequency(courseType, map.get(courseType)));
        }
        return result;
    }
}
