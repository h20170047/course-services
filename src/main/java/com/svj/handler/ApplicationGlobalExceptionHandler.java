package com.svj.handler;

import com.svj.dto.ErrorDTO;
import com.svj.dto.ServiceResponse;
import com.svj.exception.CourseServiceBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ServiceResponse<?> serviceResponse= new ServiceResponse<>();
        List<ErrorDTO> errorDTOList= new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error-> {
                    ErrorDTO errorDTO= new ErrorDTO( error.getField()+": "+ error.getDefaultMessage());
                    errorDTOList.add(errorDTO);
                });
        serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
        serviceResponse.setErrors(errorDTOList);
        return serviceResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<?> handleMethodArgumentNotValidException(HttpMessageNotReadableException exception){
        ServiceResponse<?> serviceResponse= new ServiceResponse<>();
        ErrorDTO errorDTO= new ErrorDTO(exception.getMessage());
        serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
        serviceResponse.setErrors(Arrays.asList(errorDTO));
        return serviceResponse;
    }

    @ExceptionHandler(CourseServiceBusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServiceResponse<?> handleServiceException(CourseServiceBusinessException exception){
        ServiceResponse<?> serviceResponse= new ServiceResponse<>();
        ErrorDTO errorDTO= new ErrorDTO(exception.getMessage());
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setErrors(Arrays.asList(errorDTO));
        return serviceResponse;
    }
}
