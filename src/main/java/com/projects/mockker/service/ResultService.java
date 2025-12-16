package com.projects.mockker.service;

import java.util.ArrayList;
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
	
	public List<ResultModel> saveResults(List<ResultModel>  results){
		List<ResultModel> savedResult=new ArrayList<>();
		for(ResultModel result: results) {
			try {
				if(resultRepo.existsByUserIdAndDateTime(result.getUserId(),result.getDateTime())) {
					continue;
				}else {
					result.setId(null);
					resultRepo.save(result);
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return savedResult;
		
	}
	
	

}
