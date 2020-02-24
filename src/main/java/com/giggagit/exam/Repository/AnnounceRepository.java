package com.giggagit.exam.Repository;

import java.util.List;

import com.giggagit.exam.Model.AnnounceModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AnnounceRepository
 */
@Repository
public interface AnnounceRepository extends JpaRepository<AnnounceModel, Integer>  {

    // find top 10 order by Id desc
    public List<AnnounceModel> findTop10ByOrderByIdDesc();
    
}