package com.example.demo.common.SpringSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("*");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests(requests -> requests
                .mvcMatchers("/", "/findAll", "/login", "/batch*").permitAll()  //해당 패턴의 url만 허용
                .antMatchers(HttpMethod.GET, "/api*").permitAll()
                .anyRequest().authenticated());

        http.formLogin(login -> login
                .loginPage("/login")
                .permitAll());

        http.logout(logout -> logout
                .logoutSuccessUrl("/"));

        http.csrf(csrf -> csrf.disable());

            return http.build();
    }
}
