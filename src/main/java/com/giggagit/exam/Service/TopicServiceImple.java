package com.giggagit.exam.Service;

import java.time.LocalDate;
import java.util.List;

import com.giggagit.exam.Form.ExamForm;
import com.giggagit.exam.Model.ExamModel;
import com.giggagit.exam.Model.ScoreModel;
import com.giggagit.exam.Model.TopicModel;
import com.giggagit.exam.Repository.TopicRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * TopicServiceImple
 */
@Service
public class TopicServiceImple implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImple(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Page<TopicModel> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    @Override
    public TopicModel findById(int topicId) {
        return topicRepository.findById(topicId).orElse(null);
    }

    @Override
    public Page<TopicModel> findAllByStatus(Boolean active, Pageable pageable) {
        return topicRepository.findAllByStatus(active, pageable);
    }

    @Override
    public void save(TopicModel topicModel) {
        topicRepository.save(topicModel);
    }

    @Override
    public void deleteById(int topicId) {
        topicRepository.deleteById(topicId);
    }

    @Override
    public Boolean exist(int topicId) {
        return topicRepository.existsById(topicId);
    }

    @Override
    public void newTopic(TopicModel topicModel) {
        for (int i = 0; i < topicModel.getTotalExams(); i++) {
            topicModel.addExam(new ExamModel());
        }

        save(topicModel);
    }

    @Override
    public void editExam(int topicId, ExamForm examForm) {
        TopicModel topicModel = findById(topicId);

        for (ExamModel examModel : examForm.getExamModel()) {
            topicModel.addExam(examModel);
        }

        save(topicModel);
    }

    @Override
    public Boolean examFilter(TopicModel topicModel) {
        Boolean status = false;
        LocalDate localDate = LocalDate.now();

        if (topicModel.isDoneExam() || localDate.isAfter(topicModel.getExpire()) || !topicModel.isStatus()) {
            status = true;
        }
        
        return status;
    }

    @Override
    public TopicModel examDoneFilter(TopicModel topicModel, String username) {
        List<ScoreModel> scoreModels = topicModel.getScoreModel();

        for (ScoreModel score : scoreModels) {
            if (username.equals(score.getUserModel().getUsername())) {
                topicModel.setDoneExam(true);
            }
        }

        return topicModel;
    }

}