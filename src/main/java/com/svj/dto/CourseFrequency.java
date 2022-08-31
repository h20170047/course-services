package com.svj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CourseFrequency {
    private CourseType courseType;
    private int count;
}
