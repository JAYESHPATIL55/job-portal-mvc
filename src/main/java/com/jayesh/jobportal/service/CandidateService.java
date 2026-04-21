package com.jayesh.jobportal.service;

import com.jayesh.jobportal.dto.CandidateDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CandidateService {
    CandidateDto createCandiate(CandidateDto candidateDto);
    Page<CandidateDto> getAllCandidates(int page, int size, String sortBy, String direction);
    CandidateDto getCandidateById(Long id);
    CandidateDto updateCandidate(Long id, CandidateDto candidateDto);
    void deleteCandidate(Long id);
    /** For dropdown selects in MVC views */
    List<CandidateDto> getAllCandidatesForDropdown();
}
