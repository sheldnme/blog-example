package com.xxs.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yajund
 * @version 1.0.0
 * @ClassName ThreadPoolConfig
 * @description
 * @date created in 14:45 2020/3/12
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "demoFixedThreadPool")
    public ExecutorService demoFixedThreadPool() {
        return Executors.newCachedThreadPool();
    }
}
