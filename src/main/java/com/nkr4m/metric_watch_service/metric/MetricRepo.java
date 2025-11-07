package com.nkr4m.metric_watch_service.metric;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepo extends JpaRepository<Metric, String> {

}
