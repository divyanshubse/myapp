package com.coaching.erp.schedule.timetable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "batch_schedules")
public class BatchSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String batchName;

	@Column(nullable = false)
	private String facultyName;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Column(nullable = false)
	private LocalDateTime endTime;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getBatchName() { return batchName; }
	public void setBatchName(String batchName) { this.batchName = batchName; }
	public String getFacultyName() { return facultyName; }
	public void setFacultyName(String facultyName) { this.facultyName = facultyName; }
	public LocalDateTime getStartTime() { return startTime; }
	public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
	public LocalDateTime getEndTime() { return endTime; }
	public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
}