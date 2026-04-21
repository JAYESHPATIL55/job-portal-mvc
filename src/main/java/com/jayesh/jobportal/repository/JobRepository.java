package com.jayesh.jobportal.repository;

import com.jayesh.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job>findByLocationIgnoreCase(String location);
}
