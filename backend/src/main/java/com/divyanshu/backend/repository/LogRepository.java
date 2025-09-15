package com.divyanshu.backend.repository;

import com.divyanshu.backend.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogRepository extends JpaRepository<Log,Integer> {
}
