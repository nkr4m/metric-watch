package com.nkr4m.metric_watch_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nkr4m.metric_watch_service.entity.User;
import com.nkr4m.metric_watch_service.metric.Monitor;
import com.nkr4m.metric_watch_service.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Monitor(metricType = "USER", componentType = "FETCH_ALL")
	public void fetchAll() throws Exception {
		List<User> res = userRepo.findAll();
	}
}
