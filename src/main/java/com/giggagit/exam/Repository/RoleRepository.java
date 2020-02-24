package com.giggagit.exam.Repository;

import com.giggagit.exam.Model.RoleModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {

    public RoleModel findByName(String name);

}