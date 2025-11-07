package com.nkr4m.metric_watch_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nkr4m.metric_watch_service.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public void fetchAll() throws Exception {
		userService.fetchAll();
	}

}
