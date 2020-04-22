package com.http.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class Config {

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

    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "spring.primary")
    public DataSource dataSource2() {

//        return DataSourceBuilder.create().build();

        DruidDataSource ds = new DruidDataSource();

        /*
         * 基本属性
         */
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@20.26.24.150:1521:CSH150");
        ds.setUsername("msgpush");
        ds.setPassword("msgpush");

        /*
         * 配置初始化大小、最小、最
         */
        ds.setInitialSize(1);
        ds.setMinIdle(1);
        ds.setMaxActive(10);

        /*
         * 配置获取连接等待超时的时间
         */
        ds.setMaxWait(6000);

        /*
         * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
         */
        ds.setTimeBetweenEvictionRunsMillis(60000);

        /*
         * 配置一个连接在池中最小生存的时间，单位是毫秒
         */
        ds.setMinEvictableIdleTimeMillis(300000);

        ds.setValidationQuery("SELECT 'X' FROM DUAL");
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);

        /*
         * 打开PSCache，并且指定每个连接上PSCache的大小
         */
        ds.setPoolPreparedStatements(false);
        ds.setMaxPoolPreparedStatementPerConnectionSize(20);
        /*
         * 配置监控统计拦截的filters
         */
        //ds.setFilters("stat");

        return ds;
    }
}
