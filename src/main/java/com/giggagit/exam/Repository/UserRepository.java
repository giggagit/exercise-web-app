package com.giggagit.exam.Repository;

import com.giggagit.exam.Model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    public UserModel findByUsername(String username);
    
}