package com.giggagit.exam.Service;

import java.time.LocalDate;
import java.util.List;

import com.giggagit.exam.Model.AnnounceModel;
import com.giggagit.exam.Repository.AnnounceRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * AnnounceServiceImpl
 */
@Service
public class AnnounceServiceImpl implements AnnounceService {

    private final AnnounceRepository announceRepository;

    public AnnounceServiceImpl(AnnounceRepository announceRepository) {
        this.announceRepository = announceRepository;
    }

    @Override
    public Page<AnnounceModel> findAll(Pageable pageable) {
        return announceRepository.findAll(pageable);
    }

    @Override
    public List<AnnounceModel> findTop10O() {
        return announceRepository.findTop10ByOrderByIdDesc();
    }

    @Override
    public void save(AnnounceModel announceModel) {
        LocalDate localDate = LocalDate.now();
        announceModel.setDate(localDate);
        announceRepository.save(announceModel);
    }

    @Override
    public void deleteById(int announceId) {
        announceRepository.deleteById(announceId);
    }

    @Override
    public Boolean exist(int announceId) {
        return announceRepository.existsById(announceId);
    }

}