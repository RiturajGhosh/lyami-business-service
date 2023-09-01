/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */

package com.lyami.v1.authentication.config;

import com.lyami.v1.authentication.interceptor.AuthFailureHandlerInterceptor;
import com.lyami.v1.authentication.interceptor.AuthInterceptor;
import com.lyami.v1.authentication.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private UserDetailsServiceImpl userDetailsService;

    private AuthFailureHandlerInterceptor authFailureHandlerInterceptor;

    private AuthInterceptor authInterceptor;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthFailureHandlerInterceptor authFailureHandlerInterceptor,
                             AuthInterceptor authInterceptor) {
        this.userDetailsService = userDetailsService;
        this.authFailureHandlerInterceptor = authFailureHandlerInterceptor;
        this.authInterceptor = authInterceptor;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChainConfig(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authFailureHandlerInterceptor))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/v1/authenticate/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authInterceptor, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
