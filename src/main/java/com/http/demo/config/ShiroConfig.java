package com.http.demo.config;

import com.http.demo.shiro.UserRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {

        DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();

        shiroFilterChainDefinition.addPathDefinition("/logout","anon");
        shiroFilterChainDefinition.addPathDefinition("/login", "anon");
        shiroFilterChainDefinition.addPathDefinition("/login/**", "anon");
        shiroFilterChainDefinition.addPathDefinition("/druid/**", "anon");
        shiroFilterChainDefinition.addPathDefinition("/api/**", "anon");
        shiroFilterChainDefinition.addPathDefinition("/**", "authc");

        return shiroFilterChainDefinition;
    }

    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }
}
