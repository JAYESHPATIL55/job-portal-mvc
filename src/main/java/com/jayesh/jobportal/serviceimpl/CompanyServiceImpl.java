package com.jayesh.jobportal.serviceimpl;

import com.jayesh.jobportal.dto.CompanyDto;
import com.jayesh.jobportal.entity.Company;
import com.jayesh.jobportal.exception.CompanyException;
import com.jayesh.jobportal.repository.CompanyRepository;
import com.jayesh.jobportal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = Company.builder()
                .name(companyDto.getName())
                .location(companyDto.getLocation())
                .build();
        return mapToDto(companyRepository.save(company));
    }

    @Override
    public Page<CompanyDto> getAllCompanies(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return companyRepository.findAll(pageable).map(this::mapToDto);
    }

    @Override
    public CompanyDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyException("Company not found with id: " + id));
        return mapToDto(company);
    }

    @Override
    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyException("Company not found with id: " + id));
        company.setName(companyDto.getName());
        company.setLocation(companyDto.getLocation());
        return mapToDto(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyException("Company not found with id: " + id));
        companyRepository.delete(company);
    }

    @Override
    public List<CompanyDto> getAllCompaniesForDropdown() {
        return companyRepository.findAll(Sort.by("name").ascending())
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CompanyDto mapToDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .location(company.getLocation())
                .build();
    }
}
