package com.jayesh.jobportal.service;

import com.jayesh.jobportal.dto.CompanyDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CompanyService {
    CompanyDto createCompany(CompanyDto companyDto);
    Page<CompanyDto> getAllCompanies(int page, int size, String sortBy, String direction);
    CompanyDto getCompanyById(Long id);
    CompanyDto updateCompany(Long id, CompanyDto companyDto);
    void deleteCompany(Long id);
    /** For dropdown selects in MVC views */
    List<CompanyDto> getAllCompaniesForDropdown();
}
