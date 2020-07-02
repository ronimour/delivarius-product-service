package com.delivery.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("!keycloak")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/v1/product/**" )
                        .hasRole("PRODUCT_READ")
                    .antMatchers(HttpMethod.POST, "/api/v1/product/**")
                        .hasRole("PRODUCT_WRITE")
                .anyRequest().authenticated()
                .and()
                    .oauth2ResourceServer()
                        .jwt();
    }
}
