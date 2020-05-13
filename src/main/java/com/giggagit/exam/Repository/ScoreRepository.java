package com.giggagit.exam.Repository;

import com.giggagit.exam.Model.ScoreModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ScoreRepository
 */
@Repository
public interface ScoreRepository extends JpaRepository<ScoreModel, Integer> {

    public Page<ScoreModel> findByTopicModelId(Integer topicId, Pageable pageable);

}