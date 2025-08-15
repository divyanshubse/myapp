package com.coaching.erp.auth.auth;

import com.coaching.erp.auth.jwt.JwtService;
import com.coaching.erp.auth.user.User;
import com.coaching.erp.auth.user.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public AuthController(UserRepository userRepository, JwtService jwtService) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestParam @NotBlank String username,
									 @RequestParam @NotBlank String password) {
		if (userRepository.existsByUsername(username)) {
			return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
		}
		User user = new User();
		user.setUsername(username);
		user.setPasswordHash(passwordEncoder.encode(password));
		user.setRoles(Set.of("ROLE_STUDENT"));
		userRepository.save(user);
		String token = jwtService.generateToken(username, List.copyOf(user.getRoles()));
		return ResponseEntity.ok(Map.of("token", token));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam @NotBlank String username,
								  @RequestParam @NotBlank String password) {
		return userRepository.findByUsername(username)
			.map(user -> {
				if (!passwordEncoder.matches(password, user.getPasswordHash())) {
					return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
				}
				String token = jwtService.generateToken(username, List.copyOf(user.getRoles()));
				return ResponseEntity.ok(Map.of("token", token));
			}).orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
	}
}