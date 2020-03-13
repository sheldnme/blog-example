package com.xxs.example.blog.dal.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yajun
 * @version 1.0.0
 * @ClassName DbConfig
 * @description
 * @date created in 17:26 2020/3/11
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
@EnableTransactionManagement
@Slf4j
public class DbConfig {

    @Resource
    private DataSourceProperties dataSourceProperties;

    private Map<String, Object> datasourceMap;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        datasourceMap = new HashMap<String, Object>();
        datasourceMap.put("driverClassName", "com.mysql.jdbc.Driver");
        datasourceMap.put("initialSize", dataSourceProperties.getInitialSize());
        datasourceMap.put("maxActive", dataSourceProperties.getMaxActive());
        datasourceMap.put("minIdle", dataSourceProperties.getMinIdle());
        datasourceMap.put("maxWait", dataSourceProperties.getMaxWait());
        datasourceMap.put("removeAbandoned", dataSourceProperties.getRemoveAbandoned());
        datasourceMap.put("removeAbandonedTimeout", dataSourceProperties.getRemoveAbandonedTimeout());
        datasourceMap.put("timeBetweenEvictionRunsMillis", dataSourceProperties.getTimeBetweenEvictionRunsMillis());
        datasourceMap.put("minEvictableIdleTimeMillis", dataSourceProperties.getMinEvictableIdleTimeMillis());
        datasourceMap.put("validationQuery", dataSourceProperties.getValidationQuery());
        datasourceMap.put("testWhileIdle", dataSourceProperties.getTestWhileIdle());
        datasourceMap.put("testOnBorrow", dataSourceProperties.getTestOnBorrow());
        datasourceMap.put("testOnReturn", dataSourceProperties.getTestOnReturn());
        datasourceMap.put("poolPreparedStatements", dataSourceProperties.getPoolPreparedStatements());
        datasourceMap.put("maxPoolPreparedStatementPerConnectionSize", dataSourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
        datasourceMap.put("initConnectionSqls", dataSourceProperties.getInitConnectionSqls());

        for (Iterator<PropertySource<?>> it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource<?> propertySource = it.next();
            this.getPropertiesFromSource(propertySource, datasourceMap);
        }
    }

    @Bean(name = "dataSourceDemo")
    @Qualifier("dataSourceDemo")
    public DataSource dataSourceDemo() {
        log.debug("初始化数据源demo");
        return this.getDataSource(dataSourceProperties.getDbUrl(), dataSourceProperties.getDbUser(), dataSourceProperties.getDbPass());
    }

    @Bean(name = "templateDemo")
    public JdbcTemplate templateDemo() {
        return new JdbcTemplate(this.dataSourceDemo());
    }


    @Bean(name = "transactionDemo")
    @Qualifier("transactionDemo")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSourceDemo());
    }

    @Bean(name = "demoTransactionTemplate")
    public TransactionTemplate demoTransactionTemplate() {
        return new TransactionTemplate(transactionManager());
    }

    private DataSource getDataSource(String url, String user, String pass) {
        datasourceMap.put(DruidDataSourceFactory.PROP_URL, url);
        datasourceMap.put(DruidDataSourceFactory.PROP_USERNAME, user);
        datasourceMap.put(DruidDataSourceFactory.PROP_PASSWORD, pass);

        try {
            return DruidDataSourceFactory.createDataSource(datasourceMap);
        } catch (Exception e) {
            log.error("无法获得数据源[{}]:[{}]", url, e);
            throw new RuntimeException("无法获得数据源.");
        }
    }

    private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
                map.put(key, propertySource.getProperty(key));
            }
        }
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
                getPropertiesFromSource(s, map);
            }
        }
    }

}
