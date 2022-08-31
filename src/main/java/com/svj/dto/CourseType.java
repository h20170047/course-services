package com.svj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CourseType {
    @JsonProperty("Live") // How can we map values Live, 1 to the same property?
    Live("Live", 1),
    @JsonProperty("Recording")
    RECORDING("Recording", 0);
    private String courseTypeText;
    private int courseVal;
    CourseType(String courseTypeText, int val){
        this.courseTypeText= courseTypeText;
        courseVal= val;
    }

    public String getCourseTypeText() {
        return courseTypeText;
    }

    public int getCourseVal() {
        return courseVal;
    }
}