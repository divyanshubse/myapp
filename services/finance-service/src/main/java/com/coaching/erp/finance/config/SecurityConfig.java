package com.coaching.erp.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(registry -> registry
				.requestMatchers("/actuator/**", "/finance/**").permitAll()
				.anyRequest().permitAll()
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}