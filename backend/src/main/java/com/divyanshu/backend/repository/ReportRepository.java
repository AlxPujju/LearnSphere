package com.divyanshu.backend.repository;


import com.divyanshu.backend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {


}
