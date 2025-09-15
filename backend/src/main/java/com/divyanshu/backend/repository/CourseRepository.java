package com.divyanshu.backend.repository;


import com.divyanshu.backend.model.Course;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course , Integer> {
    Page<Course> findAll(Pageable pageable);


}
