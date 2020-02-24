package com.giggagit.exam.Service;

import java.util.List;

import com.giggagit.exam.Model.AnnounceModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * AnnounceService
 */
public interface AnnounceService {

    public Page<AnnounceModel> findAll(Pageable pageable);
    public List<AnnounceModel> findTop10O();
    public void save(AnnounceModel announceModel);
    public void deleteById(int announceId);
    public Boolean exist(int announceId);

}