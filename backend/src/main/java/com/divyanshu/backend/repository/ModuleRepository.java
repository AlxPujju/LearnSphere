package com.divyanshu.backend.repository;


import com.divyanshu.backend.model.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ModuleRepository extends JpaRepository<Module , Integer> {
    Page<Module> findAll(Pageable pageable);
}


