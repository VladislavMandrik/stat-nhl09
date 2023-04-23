package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mir")
                .password("mir")
                .roles("ADMIN")
                .and()
                .withUser("Artemy")
                .password("Artemy0701")
                .roles("USER")
                .and()
                .withUser("prostoromantic")
                .password("kolokol5000")
                .roles("USER")
                .and()
                .withUser("ZHLOBIK")
                .password("artemmmnhl2023")
                .roles("USER")
                .and()
                .withUser("user")
                .password("user")
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
//                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/statistic/create").permitAll()
//                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/table/create").permitAll()
//                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/statistic/stats").permitAll()
                .antMatchers(HttpMethod.GET, "/statistic/goalie").permitAll()
                .antMatchers(HttpMethod.GET, "/statistic/defenders").permitAll()
                .antMatchers(HttpMethod.GET, "/statistic/transfers").permitAll()
                .antMatchers(HttpMethod.GET, "/table/standings").permitAll()
                .antMatchers(HttpMethod.GET, "/table/teamstats").permitAll()
                .antMatchers(HttpMethod.GET, "/statistic/uploaded").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/statistic/upload").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/statistic/downloadP").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/statistic/downloadT").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/statistic/logs").hasRole("ADMIN")
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
