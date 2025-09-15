package com.divyanshu.backend.repository;


import com.divyanshu.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User , Integer> {


    User findByName(String name);
}
