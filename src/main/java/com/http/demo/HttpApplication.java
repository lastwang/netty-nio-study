package com.http.demo;

import com.http.demo.serlet.ServletX;
import com.http.demo.user.User;
import com.http.demo.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class HttpApplication extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(HttpApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HttpApplication.class, args);

    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HttpApplication.class);
    }

    @Bean
    public ServletRegistrationBean weChatValid(){
        //第一个参数是第1步中创建的WeChatServlet实例，第二个参数是其对应的路径，相当于web.xml中配置时的url-pattern。
        return new ServletRegistrationBean(new ServletX(), "/ddd");
    }
}
