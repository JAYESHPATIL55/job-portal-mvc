package com.jayesh.jobportal.controller;

import com.jayesh.jobportal.dto.CandidateDto;
import com.jayesh.jobportal.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateDto> createCandidate(
            @Valid @RequestBody CandidateDto candidateDto) {
        return ResponseEntity.ok(candidateService.createCandiate(candidateDto));
    }

    @GetMapping
    public ResponseEntity<Page<CandidateDto>> getAllCandidates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                candidateService.getAllCandidates(page, size, sortBy, direction)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> updateCandidate(
            @PathVariable Long id,
            @Valid @RequestBody CandidateDto candidateDto) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, candidateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.ok("Candidate deleted successfully");
    }
}
