package com.http.demo;

import com.http.demo.user.User;
import com.http.demo.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class HttpApplication {

    private static final Logger log = LoggerFactory.getLogger(HttpApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HttpApplication.class, args);

        UserMapper userMapper = run.getBean(UserMapper.class);

        DataSource bean = run.getBean(DataSource.class);
        Object bean2 = run.getBean("dataSource2");
        List<User> all = userMapper.getAll();
        log.info("数据:{}", bean);
    }

}
