package com.svj.annotation;

import com.svj.dto.CourseType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CourseTypeValidator implements ConstraintValidator<CourseTypeValidation, CourseType> {
    @Override
    public boolean isValid(CourseType courseType , ConstraintValidatorContext constraintValidatorContext) {
        List list= Arrays.asList("Live", "Recording");
        return list.contains(courseType.getCourseAlias());
    }
}
