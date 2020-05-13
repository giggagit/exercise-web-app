package com.giggagit.exam.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * ExamTopicsModel
 */
@Entity
@Table(name = "topics")
public class TopicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, max = 255)
    private String title;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate expire;

    private Boolean status;

    @Min(1)
    private int totalExams;

    @Min(1)
    private int passScore;

    @Transient
    private Boolean doneExam = false;

    @OneToMany(mappedBy = "topicModel", cascade = CascadeType.ALL)
    private List<ExamModel> examModel = new ArrayList<>();

    @OneToMany(mappedBy = "topicModel", cascade = CascadeType.ALL)
    private List<ScoreModel> scoreModel = new ArrayList<>();

    public void addExam(ExamModel examModel) {
        if (this.examModel == null) {
            this.examModel = new ArrayList<>();
        }

        this.examModel.add(examModel);
        examModel.setTopicModel(this);
    }

    public void removeExam(ExamModel examModel) {
        this.examModel.remove(examModel);
        examModel.setTopicModel(null);
    }

    public void addScore(ScoreModel scoreModel) {
        if (this.scoreModel == null) {
            this.scoreModel = new ArrayList<>();
        }

        this.scoreModel.add(scoreModel);
        scoreModel.setTopicModel(this);
    }

    public void removeScore(ScoreModel scoreModel) {
        this.scoreModel.remove(scoreModel);
        scoreModel.setTopicModel(null);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getExpire() {
        return this.expire;
    }

    public void setExpire(LocalDate expire) {
        this.expire = expire;
    }

    public Boolean isStatus() {
        return this.status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getTotalExams() {
        return this.totalExams;
    }

    public void setTotalExams(int totalExams) {
        this.totalExams = totalExams;
    }

    public int getPassScore() {
        return this.passScore;
    }

    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    public Boolean isDoneExam() {
        return this.doneExam;
    }

    public Boolean getDoneExam() {
        return this.doneExam;
    }

    public void setDoneExam(Boolean doneExam) {
        this.doneExam = doneExam;
    }

    public List<ExamModel> getExamModel() {
        return this.examModel;
    }

    public void setExamModel(List<ExamModel> examModel) {
        this.examModel = examModel;
    }

    public List<ScoreModel> getScoreModel() {
        return this.scoreModel;
    }

    public void setScoreModel(List<ScoreModel> scoreModel) {
        this.scoreModel = scoreModel;
    }

}