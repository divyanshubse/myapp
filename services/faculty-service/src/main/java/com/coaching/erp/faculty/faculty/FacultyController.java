package com.coaching.erp.faculty.faculty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
	private final FacultyRepository repository;

	public FacultyController(FacultyRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Faculty> list() {
		return repository.findAll();
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid FacultyCreateRequest request) {
		if (repository.existsByEmail(request.email())) {
			return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
		}
		Faculty f = new Faculty();
		f.setFullName(request.fullName());
		f.setEmail(request.email());
		f.setSpecialization(request.specialization());
		f = repository.save(f);
		return ResponseEntity.created(URI.create("/faculty/" + f.getId())).body(f);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Faculty> get(@PathVariable Long id) {
		return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid FacultyUpdateRequest request) {
		return repository.findById(id).map(f -> {
			f.setFullName(request.fullName());
			f.setSpecialization(request.specialization());
			return ResponseEntity.ok(repository.save(f));
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

	public record FacultyCreateRequest(@NotBlank String fullName, @Email String email, String specialization) {}
	public record FacultyUpdateRequest(@NotBlank String fullName, String specialization) {}
}