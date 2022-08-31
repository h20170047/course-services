package com.svj.repository;

import com.svj.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {
}
