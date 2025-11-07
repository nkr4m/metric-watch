package com.nkr4m.metric_watch_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nkr4m.metric_watch_service.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
