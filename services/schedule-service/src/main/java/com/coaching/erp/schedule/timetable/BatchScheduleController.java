package com.coaching.erp.schedule.timetable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class BatchScheduleController {
	private final BatchScheduleRepository repository;

	public BatchScheduleController(BatchScheduleRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<BatchSchedule> list() { return repository.findAll(); }

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid CreateScheduleRequest request) {
		BatchSchedule s = new BatchSchedule();
		s.setBatchName(request.batchName());
		s.setFacultyName(request.facultyName());
		s.setStartTime(request.startTime());
		s.setEndTime(request.endTime());
		s = repository.save(s);
		return ResponseEntity.created(URI.create("/schedule/" + s.getId())).body(s);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BatchSchedule> get(@PathVariable Long id) {
		return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UpdateScheduleRequest request) {
		return repository.findById(id).map(s -> {
			s.setBatchName(request.batchName());
			s.setFacultyName(request.facultyName());
			s.setStartTime(request.startTime());
			s.setEndTime(request.endTime());
			return ResponseEntity.ok(repository.save(s));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!repository.existsById(id)) return ResponseEntity.notFound().build();
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	public record CreateScheduleRequest(@NotBlank String batchName, @NotBlank String facultyName,
									  @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {}
	public record UpdateScheduleRequest(@NotBlank String batchName, @NotBlank String facultyName,
									  @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {}
}