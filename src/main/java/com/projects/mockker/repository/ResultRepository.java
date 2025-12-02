package com.projects.mockker.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.mockker.model.ResultModel;

@Repository
public interface ResultRepository extends JpaRepository<ResultModel, Long> {
//    List<ResultModel> findByUserId(Long userId);
    List<ResultModel> findByUserIdOrderByDateTimeDesc(Long userId);
}
