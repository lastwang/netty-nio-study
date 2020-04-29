package com.http.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl("jdbc:mariadb://127.0.0.1:3306/test");
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");

        dataSource.setUsername("root");
        dataSource.setPassword("wang");

        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "dataSource2")
    public DataSource dataSource2() {

        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl("jdbc:mariadb://127.0.0.1:3306/test");
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");

        dataSource.setUsername("root");
        dataSource.setPassword("wang");
        /*
         * 配置初始化大小、最小、最
         */
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);

        /*
         * 配置获取连接等待超时的时间
         */
        dataSource.setMaxWait(6000);

        /*
         * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
         */
        dataSource.setTimeBetweenEvictionRunsMillis(60000);

        /*
         * 配置一个连接在池中最小生存的时间，单位是毫秒
         */
        dataSource.setMinEvictableIdleTimeMillis(300000);

        dataSource.setValidationQuery("SELECT 'X' FROM DUAL");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        /*
         * 打开PSCache，并且指定每个连接上PSCache的大小
         */
        dataSource.setPoolPreparedStatements(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        /*
         * 配置监控统计拦截的filters
         */
        try {
            dataSource.setFilters("stat");
        } catch (SQLException throwables) {
            log.error("配置监控统计拦截的filter error",throwables);
        }

        return dataSource;
    }
}
