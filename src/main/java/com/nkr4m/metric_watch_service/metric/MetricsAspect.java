package com.nkr4m.metric_watch_service.metric;

import java.util.concurrent.CompletableFuture;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MetricsAspect {
    
    @Autowired
    private MetricRepo metricRepository;
    
    @Around("@annotation(Monitor)")
    public Object monitor(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Monitor annotation = getAnnotation(joinPoint);
        String metricType = annotation.metricType();
        String componentType = annotation.componentType();
        
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            
            // Save metric asynchronously using CompletableFuture
            saveMetricAsync(metricType, componentType, duration, true, null);
            
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - startTime;
            
            // Save metric asynchronously using CompletableFuture
            saveMetricAsync(metricType, componentType, duration, false, ex.getMessage());
            
            throw ex;
        }
    }
    
    private void saveMetricAsync(String metricType, String componentType, long duration, 
                                 boolean success, String errorMessage) {
        CompletableFuture.runAsync(() -> {
            Metric metric = new Metric();
            metric.setMetricType(metricType);
            metric.setComponentType(componentType);
            metric.setDuration(duration);
            metric.setSuccess(success);
            metric.setErrorMessage(errorMessage);
            metric.setTimestamp(System.currentTimeMillis());
            
            metricRepository.save(metric);
            System.out.println("Metric saved asynchronously: " + metricType + " - " + componentType);
        });
    }
    
    private Monitor getAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(Monitor.class);
    }
}

