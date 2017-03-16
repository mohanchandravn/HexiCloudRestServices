package com.hexicloud.portaldb.configuration;

import com.hexicloud.portaldb.auth.JwtAuthFilter;
import com.hexicloud.portaldb.auth.JwtAuthenticationEntryPoint;
import com.hexicloud.portaldb.auth.JwtAuthenticationProvider;

import com.hexicloud.portaldb.controller.ClmDataController;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = Logger.getLogger(SecurityConfig.class);
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthEndPoint;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("Setting the jwtAuthenticationProvider as authentication provider in configure auth");
        auth.authenticationProvider(jwtAuthenticationProvider);
        logger.info("Done setting the jwtAuthenticationProvider as authentication provider  in configure auth");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Starting the configure http");
        
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
            .antMatchers("/portal/login").permitAll()
            .antMatchers("/admin/login").permitAll()
            .antMatchers("/**/*").hasAuthority("ROLE_USER")
//            .antMatchers("/portal/*").access("hasRole('ROLE_USER')")
//            .antMatchers("/admin/*").access("hasRole('ROLE_ADMIN')")
//            .antMatchers("/common/*").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthEndPoint);
        logger.info("Ending the configure http");
    }
}
