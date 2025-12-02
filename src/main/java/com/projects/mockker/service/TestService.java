package com.projects.mockker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.mockker.model.TestModel;
import com.projects.mockker.model.TestTopicModel;
import com.projects.mockker.repository.TestRepository;
import com.projects.mockker.repository.TestTopicRepository;

@Service
public class TestService {
	@Autowired
	TestRepository testRepo;
	
	@Autowired 
	TestTopicRepository topicRepo;

	public List<TestModel> getAlltests() {								//---------------get all tests
		return testRepo.findAll();
	}
	
	public TestModel addTest(TestModel test) {								//---------------add test
        return testRepo.save(test);
    }

	public List<TestModel> addTests(List<TestModel> tests) {								//---------------add multiple tests
		return testRepo.saveAll(tests);
	}


	public List<TestTopicModel> getTopicsByTest(Long test) {								//---------------get topics by test
		return topicRepo.findByTest(test);
	}

}
