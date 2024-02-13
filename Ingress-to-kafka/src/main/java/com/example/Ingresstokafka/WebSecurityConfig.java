package com.example.Ingresstokafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
/*
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
    .authorizeRequests()
    .antMatchers("/", "/index").hasAnyRole()
    .antMatchers("/result").permitAll()
    .antMatchers("/send-message").permitAll()
    .antMatchers("/upload").permitAll()
    .antMatchers("/authenticate").permitAll()
    .and()
    .formLogin()
    .loginPage("/login.html")
    .loginProcessingUrl("/authenticate")
    .defaultSuccessUrl("/index.html",true)
    .failureUrl("/login.html?error=true")
    .permitAll()
    .and()
    .logout()
    .permitAll()
    .and()
    .csrf().ignoringAntMatchers("/send-message", "/upload", "/authenticate");


    return http.build();
}
}*/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user")
            .password("{noop}password")
            .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/result").permitAll()
                .antMatchers("/send-message").permitAll()
                .antMatchers("/upload").permitAll()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
            .logout()
                .permitAll()
            .and()
            .csrf().ignoringAntMatchers("/send-message", "/upload", "/authenticate");
    }
}


