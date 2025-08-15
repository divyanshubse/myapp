package com.coaching.erp.faculty.faculty;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
	boolean existsByEmail(String email);
}