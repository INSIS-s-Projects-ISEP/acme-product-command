package com.isep.acme.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableConfigurationProperties
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and().cors().disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/").permitAll()
            .and()
            .csrf().disable()
            .headers().frameOptions().disable()
            .contentSecurityPolicy("connect-src 'self'");
    }

}