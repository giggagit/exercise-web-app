package com.giggagit.exam.Service;

import com.giggagit.exam.Model.ScoreModel;
import com.giggagit.exam.Model.TopicModel;
import com.giggagit.exam.Model.UserModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ScoreService
 */
public interface ScoreService {

    public void save(ScoreModel scoreModel);
    public Page<ScoreModel> findByTopicId (int topicId, Pageable pageable);
    public ScoreModel result(int userScore, int passScore, TopicModel topicModel, UserModel userModel);
    
}