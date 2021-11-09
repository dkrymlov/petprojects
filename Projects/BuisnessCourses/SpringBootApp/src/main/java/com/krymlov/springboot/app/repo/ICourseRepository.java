package com.krymlov.springboot.app.repo;

import com.krymlov.springboot.app.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface ICourseRepository extends CrudRepository<Course, Long> {
}
