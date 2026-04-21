package com.jayesh.jobportal.repository;

import com.jayesh.jobportal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
