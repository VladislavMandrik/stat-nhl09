package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @Value("${user1.username}")
    private String user1Username;
    @Value("${user1.password}")
    private String user1Password;

    @Value("${user2.username}")
    private String user2Username;
    @Value("${user2.password}")
    private String user2Password;

    @Value("${user3.username}")
    private String user3Username;
    @Value("${user3.password}")
    private String user3Password;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests(authz -> authz
                .antMatchers(HttpMethod.GET, "/").permitAll().antMatchers(HttpMethod.GET, "/statistic/stats")
                .permitAll().antMatchers(HttpMethod.GET, "/statistic/goalie").permitAll()
                .antMatchers(HttpMethod.GET, "/statistic/defenders").permitAll()
                .antMatchers(HttpMethod.GET, "/statistic/transfers").permitAll()
                .antMatchers(HttpMethod.GET, "/table/standings").permitAll()
                .antMatchers(HttpMethod.GET, "/table/teamstats").permitAll()
                .antMatchers(HttpMethod.POST, "/statistic/create").permitAll()
                .antMatchers(HttpMethod.POST, "/table/create").permitAll()
                .antMatchers(HttpMethod.GET, "/statistic/uploaded").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/statistic/upload").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/statistic/downloadP").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/statistic/downloadT").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/statistic/logs").hasRole("ADMIN")
                .antMatchers( "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()).formLogin().permitAll().and().logout().permitAll();
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin =
                User.builder().username(adminUsername).password(passwordEncoder().encode(adminPassword)).roles("ADMIN").build();
        UserDetails user1 =
                User.builder().username(user1Username).password(passwordEncoder().encode(user1Password)).roles("USER").build();
        UserDetails user2 =
                User.builder().username(user2Username).password(passwordEncoder().encode(user2Password)).roles("USER").build();
        UserDetails user3 =
                User.builder().username(user3Username).password(passwordEncoder().encode(user3Password)).roles("USER").build();
        return new InMemoryUserDetailsManager(admin, user1, user2, user3);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

