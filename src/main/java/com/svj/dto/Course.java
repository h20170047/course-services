package com.svj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int courseId;
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
