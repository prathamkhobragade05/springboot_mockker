package com.projects.mockker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.mockker.model.ResultModel;
import com.projects.mockker.repository.ResultRepository;

@Service
public class ResultService {
	@Autowired
	ResultRepository resultRepo;

	public List<ResultModel> getResultByUser(Long userId){								//---------------get Result by userId
		return resultRepo.findByUserIdOrderByDateTimeDesc(userId);
	}
	
	public ResultModel saveResult(ResultModel result) {								//---------------save result
	    ResultModel savedResult = resultRepo.save(result);
	    try {

		    List<ResultModel> results = resultRepo.findByUserIdOrderByDateTimeDesc(result.getUserId());


		    if (results.size() > 10) {
		        List<ResultModel> toDelete = results.subList(10, results.size());
		        resultRepo.deleteAll(toDelete);
		    }
	    }catch(Exception e) {
	    	
	    }

	    return savedResult;
	}

	
	

}
