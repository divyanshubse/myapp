package com.coaching.erp.auth.config;

import com.coaching.erp.auth.user.User;
import com.coaching.erp.auth.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {
	@Bean
	CommandLineRunner initUsers(UserRepository userRepository) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return args -> {
			if (!userRepository.existsByUsername("admin")) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setPasswordHash(encoder.encode("admin123"));
				admin.setRoles(Set.of("ROLE_ADMIN"));
				userRepository.save(admin);
			}
		};
	}
}