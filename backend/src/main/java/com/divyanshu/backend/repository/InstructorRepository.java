package com.divyanshu.backend.repository;


import com.divyanshu.backend.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InstructorRepository extends JpaRepository<Instructor , Integer> {
}


