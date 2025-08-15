package com.coaching.erp.student.student;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
	private final StudentRepository repository;

	public StudentController(StudentRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Student> list() {
		return repository.findAll();
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid StudentCreateRequest request) {
		if (repository.existsByEmail(request.email())) {
			return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
		}
		Student s = new Student();
		s.setFullName(request.fullName());
		s.setEmail(request.email());
		s.setPhone(request.phone());
		s = repository.save(s);
		return ResponseEntity.created(URI.create("/students/" + s.getId())).body(s);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Student> get(@PathVariable Long id) {
		return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid StudentUpdateRequest request) {
		return repository.findById(id).map(s -> {
			s.setFullName(request.fullName());
			s.setPhone(request.phone());
			return ResponseEntity.ok(repository.save(s));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public record StudentCreateRequest(@NotBlank String fullName, @Email String email, String phone) {}
	public record StudentUpdateRequest(@NotBlank String fullName, String phone) {}
}