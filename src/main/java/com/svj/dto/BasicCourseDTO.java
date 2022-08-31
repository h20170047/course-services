package com.svj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicCourseDTO {
    private String name;
    private String trainerName;
    private String courseDescription;
    private String duration; //days
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy") // @JsonFormat annotation fails to map string to date without noArgsConstructor
    private Date startDate;
    private CourseType courseType; //live or recording
    private double fees;
    private boolean isCertificateAvailable;
}
