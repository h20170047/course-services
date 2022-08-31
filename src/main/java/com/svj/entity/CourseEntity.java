package com.svj.entity;

import com.svj.dto.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="COURSE_TBL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String name;
    private String trainerName;
    private String courseDescription;
    private String duration; //days
    private Date startDate;
    private CourseType courseType; //live or recording
    private double fees;
    private boolean isCertificateAvailable;
    private String email;
    private String contact;
}
