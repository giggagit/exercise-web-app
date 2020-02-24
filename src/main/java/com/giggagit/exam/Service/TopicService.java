package com.giggagit.exam.Service;

import com.giggagit.exam.Form.ExamForm;
import com.giggagit.exam.Model.TopicModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TopicService
 */
public interface TopicService {

    public Page<TopicModel> findAll(Pageable pageable);
    public TopicModel findById(int topicId);
    public Page<TopicModel> findAllByStatus(Boolean active, Pageable pageable);
    public void save(TopicModel topicModel);
    public void deleteById(int topicId);
    public Boolean exist(int topicId);
    public void newTopic(TopicModel topicModel);
    public void editExam(int topicId, ExamForm examForm);
    public Boolean examFilter(TopicModel topicModel);
    public TopicModel examDoneFilter(TopicModel topicModel, String username);

}