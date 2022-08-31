package com.svj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.svj.annotation.CourseTypeValidation;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BasicCourseDTO {
    @NotBlank(message= "Course name shouldn't be NULL OR EMPTY")
    private String name;
    @NotEmpty(message = "Trainer name should be defined")
    private String trainerName;
    @Length(min = 5, max = 20, message = "Description must be present")
    private String courseDescription;
    @NotNull(message = "Course Duration must be specified")
    private String duration; //days
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy") // @JsonFormat annotation fails to map string to date without noArgsConstructor
    @Future(message = "Start date can't be past date")
    private Date startDate;
    @CourseTypeValidation
    private CourseType courseType; //live or recording
    @Min(value = 1500, message = "Course price can't be lesser than 1500")
    @Max(value = 5000, message = "Course price can't be more than 5000")
    private double fees;
    private boolean isCertificateAvailable;
    @Email(message = "Invalid email ID")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$")
    private String contact;
}
