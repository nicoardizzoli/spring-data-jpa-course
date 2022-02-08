package com.jpa.nicoardizzolidev.repo;

import com.jpa.nicoardizzolidev.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
