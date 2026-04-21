package com.jayesh.jobportal.serviceimpl;

import com.jayesh.jobportal.dto.CandidateDto;
import com.jayesh.jobportal.entity.Candidate;
import com.jayesh.jobportal.exception.CandidateException;
import com.jayesh.jobportal.repository.CandidateRepository;
import com.jayesh.jobportal.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    @Override
    public CandidateDto createCandiate(CandidateDto candidateDto) {
        Candidate candidate = Candidate.builder()
                .name(candidateDto.getName())
                .email(candidateDto.getEmail())
                .skills(candidateDto.getSkills())
                .experience(candidateDto.getExperience())
                .build();
        return mapToDto(candidateRepository.save(candidate));
    }

    @Override
    public Page<CandidateDto> getAllCandidates(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return candidateRepository.findAll(pageable).map(this::mapToDto);
    }

    @Override
    public CandidateDto getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateException("Candidate not found with id: " + id));
        return mapToDto(candidate);
    }

    @Override
    public CandidateDto updateCandidate(Long id, CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateException("Candidate not found with id: " + id));
        candidate.setName(candidateDto.getName());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setSkills(candidateDto.getSkills());
        candidate.setExperience(candidateDto.getExperience());
        return mapToDto(candidateRepository.save(candidate));
    }

    @Override
    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateException("Candidate not found with id: " + id));
        candidateRepository.delete(candidate);
    }

    @Override
    public List<CandidateDto> getAllCandidatesForDropdown() {
        return candidateRepository.findAll(Sort.by("name").ascending())
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CandidateDto mapToDto(Candidate candidate) {
        return CandidateDto.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .skills(candidate.getSkills())
                .experience(candidate.getExperience())
                .build();
    }
}
