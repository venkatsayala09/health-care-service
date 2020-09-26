package com.health.care.repository;

import com.health.care.entity.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
}
