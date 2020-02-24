package com.giggagit.exam.Repository;

import com.giggagit.exam.Model.TopicModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ExamTopicsRepository
 */
@Repository
public interface TopicRepository extends JpaRepository<TopicModel, Integer> {

    Page<TopicModel> findAllByStatus(Boolean active, Pageable pageable);

}