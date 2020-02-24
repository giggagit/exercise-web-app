package com.giggagit.exam.Service;

import com.giggagit.exam.Model.ScoreModel;
import com.giggagit.exam.Model.TopicModel;
import com.giggagit.exam.Model.UserModel;
import com.giggagit.exam.Repository.ScoreRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * ScoreServiceImpl
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public void save(ScoreModel scoreModel) {
        scoreRepository.save(scoreModel);
    }

    @Override
    public Page<ScoreModel> findByTopicId (int topicId, Pageable pageable) {
        return scoreRepository.findByTopicModelId(topicId, pageable);
    }

    @Override
    public ScoreModel result(int userScore, int passScore, TopicModel topicModel, UserModel userModel) {
        Boolean finalScore = false;
        ScoreModel scoreModel = new ScoreModel();

        // Compare pass score
        if (userScore >= passScore) {
            finalScore = true;
        } else {
            finalScore = false;
        }

        scoreModel.setScore(userScore);
        scoreModel.setStatus(finalScore);
        scoreModel.setTopicModel(topicModel);
        scoreModel.setUserModel(userModel);

        save(scoreModel);
        return scoreModel;
    }

}