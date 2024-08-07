package com.example.contactsapplication.config;

import com.example.contactsapplication.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/swagger-resources"
    };


    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/userInfo").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/contacts/hallo").permitAll()
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {
                    basic.authenticationEntryPoint(customAuthenticationEntryPoint);
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer.logoutUrl("/api/users/logout");
                    httpSecurityLogoutConfigurer.invalidateHttpSession(true);
                    httpSecurityLogoutConfigurer.deleteCookies("JSESSIONID");
                    httpSecurityLogoutConfigurer.logoutSuccessHandler(customLogoutSuccessHandler);
                })
                .authenticationProvider(authenticationProvider());
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // @formatter:off
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://127.0.0.1:4200",
                "http://localhost:4200/*", "http://127.0.0.1:4200/*", "http://3.74.37.38:4200",
                "http://3.74.37.38:4200/*", "http://3.74.37.38", "http://3.74.37.38/*" ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Cache-Control", "Content-Language", "Content-Type", "Expires", "Last-Modified", "Pragma", "Location"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        // @formatter:on
        return source;
    }

}
