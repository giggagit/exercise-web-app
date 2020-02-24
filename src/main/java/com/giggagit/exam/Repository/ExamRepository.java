package com.giggagit.exam.Repository;

import com.giggagit.exam.Model.ExamModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ExamTopicsRepository
 */
@Repository
public interface ExamRepository extends JpaRepository<ExamModel, Integer> {


}