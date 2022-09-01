package com.svj.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svj.dto.CourseRequestDTO;
import com.svj.dto.CourseResponseDTO;
import com.svj.entity.CourseEntity;

public class AppUtils {
    // DTO->entity

    public static CourseEntity mapDTOToEntity(CourseRequestDTO courseRequestDTO){
        CourseEntity courseEntity = new CourseEntity();
        copyDTOToEntity(courseRequestDTO, courseEntity);
        return courseEntity;
    }

    public static void copyDTOToEntity(CourseRequestDTO courseRequestDTO, CourseEntity courseEntity) {
        courseEntity.setName(courseRequestDTO.getName());
        courseEntity.setTrainerName(courseRequestDTO.getTrainerName());
        courseEntity.setCourseDescription(courseRequestDTO.getCourseDescription());
        courseEntity.setDuration(courseRequestDTO.getDuration());
        courseEntity.setStartDate(courseRequestDTO.getStartDate());
        courseEntity.setCourseType(courseRequestDTO.getCourseType());
        courseEntity.setFees(courseRequestDTO.getFees());
        courseEntity.setCertificateAvailable(courseRequestDTO.isCertificateAvailable());
        courseEntity.setContact(courseRequestDTO.getContact());
        courseEntity.setEmail(courseRequestDTO.getEmail());
    }

    public static CourseResponseDTO mapEntityToDTO(CourseEntity courseEntity){
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourseId(courseEntity.getCourseId());
        courseResponseDTO.setName(courseEntity.getName());
        courseResponseDTO.setTrainerName(courseEntity.getTrainerName());
        courseResponseDTO.setCourseDescription(courseEntity.getCourseDescription());
        courseResponseDTO.setDuration(courseEntity.getDuration());
        courseResponseDTO.setStartDate(courseEntity.getStartDate());
        courseResponseDTO.setCourseType(courseEntity.getCourseType());
        courseResponseDTO.setFees(courseEntity.getFees());
        courseResponseDTO.setCertificateAvailable(courseEntity.isCertificateAvailable());
        courseResponseDTO.setContact(courseEntity.getContact());
        courseResponseDTO.setEmail(courseEntity.getEmail());
        return courseResponseDTO;
    }

    public static String convertObjectToJson(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
