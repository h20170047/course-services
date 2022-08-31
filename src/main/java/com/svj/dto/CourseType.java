package com.svj.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CourseType {
    LIVE( "Live"),
    RECORDING( "Recording");
    private String courseAlias;
    private CourseType(String alias){
        this.courseAlias= alias;
    }

    public String getCourseAlias() {
        return this.courseAlias;
    }

    @JsonCreator
    public static CourseType getCourseType(String value){
        for(CourseType courseType: CourseType.values()){
            if(courseType.getCourseAlias().equalsIgnoreCase(value)){
                return courseType;
            }
        }
        return null;
    }
}