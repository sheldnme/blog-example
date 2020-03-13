package com.xxs.example.blog.dal.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName DbConfig
 * @description
 * @date created in 17:11 2020/3/11
 */
@Data
@ConfigurationProperties("demo.mysql")
public class DataSourceProperties {

    @Value("${demo.mysql.url:jdbc:mysql://127.0.0.1:3306/demo?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8}")
    private String dbUrl;

    @Value("${demo.mysql.user:root}")
    private String dbUser;

    @Value("${demo.mysql.pass:123456}")
    private String dbPass;

    @Value("${demo.mysql.initial.size:20}")
    private String initialSize;

    @Value("${demo.mysql.max.active:50}")
    private String maxActive;

    @Value("${demo.mysql.minIdle:1}")
    private String minIdle;

    @Value("${demo.mysql.maxWait:20000}")
    private String maxWait;

    @Value("${demo.mysql.removeAbandoned:true}")
    private String removeAbandoned;

    @Value("${demo.mysql.removeAbandonedTimeout:180}")
    private String removeAbandonedTimeout;

    @Value("${demo.mysql.timeBetweenEvictionRunsMillis:60000}")
    private String timeBetweenEvictionRunsMillis;

    @Value("${demo.mysql.minEvictableIdleTimeMillis:300000}")
    private String minEvictableIdleTimeMillis;

    @Value("${demo.mysql.validationQuery:SELECT 1}")
    private String validationQuery;

    @Value("${demo.mysql.testWhileIdle:true}")
    private String testWhileIdle;

    @Value("${demo.mysql.testOnBorrow:false}")
    private String testOnBorrow;

    @Value("${demo.mysql.testOnReturn:false}")
    private String testOnReturn;

    @Value("${demo.mysql.poolPreparedStatements:true}")
    private String poolPreparedStatements;

    @Value("${demo.mysql.maxPoolPreparedStatementPerConnectionSize:50}")
    private String maxPoolPreparedStatementPerConnectionSize;

    @Value("${demo.mysql.initConnectionSqls:SELECT 1}")
    private String initConnectionSqls;
}
