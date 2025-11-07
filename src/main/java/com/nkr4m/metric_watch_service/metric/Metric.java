package com.nkr4m.metric_watch_service.metric;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
@Table(name = "metrics")
public class Metric {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String metricType;
    
    @Column(nullable = false)
    private String componentType;
    
    @Column(nullable = false)
    private Long timestamp;
    
    @Column(nullable = false)
    private Long duration;
    
    @Column(nullable = false)
    private Boolean success;
    
    @Column
    private String errorMessage;
    
    @Column
    private String category;
    
    @Column
    private Integer priority;
    
    @Column(columnDefinition = "JSON")
    @Convert(converter = AttributesConverter.class)
    private Map<String, Object> attributes;
    
    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = System.currentTimeMillis();
        }
    }
}
