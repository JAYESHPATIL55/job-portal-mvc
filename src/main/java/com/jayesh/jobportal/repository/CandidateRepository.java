package com.jayesh.jobportal.repository;

import com.jayesh.jobportal.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate,Long> {
}
