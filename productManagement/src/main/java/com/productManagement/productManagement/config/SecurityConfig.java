package com.productManagement.productManagement.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Development Profile Configuration (Bypass Security)
    @Bean
    @Profile("dev")
    public SecurityFilterChain securityFilterChainDev(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/user/**").hasRole("USER")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic();

        return http.build();
    }

    // Production Profile Configuration (JdbcAuthentication)
    @Bean
    @Profile("prod")
    public SecurityFilterChain securityFilterChainProd(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic();

        return http.build();
    }

    // Configuring JDBC Authentication for Prod profile
    @Bean
    @Profile("prod")
    @Primary
    public AuthenticationManagerBuilder configureJdbcAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT login, password, active FROM users WHERE login=? AND active=true")
                .authoritiesByUsernameQuery("SELECT u.login, r.name FROM users u " +
                        "JOIN user_roles ur ON u.id = ur.user_id " +
                        "JOIN role r ON ur.role_id = r.id WHERE u.login=?")
                .passwordEncoder(passwordEncoder());
        return auth;
    }

    // Configuring In-Memory Authentication for Dev profile (Optional for testing)
    @Bean
    @Profile("dev")
    public AuthenticationManagerBuilder configureInMemoryAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("devUser").password(passwordEncoder().encode("devPass")).roles("USER")
                .and()
                .withUser("devAdmin").password(passwordEncoder().encode("devAdminPass")).roles("ADMIN");
        return auth;
    }
}
