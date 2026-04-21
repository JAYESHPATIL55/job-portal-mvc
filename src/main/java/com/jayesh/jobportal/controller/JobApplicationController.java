package com.jayesh.jobportal.controller;

import com.jayesh.jobportal.dto.JobApplicationDto;
import com.jayesh.jobportal.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationDto> applyForJob(@RequestBody JobApplicationDto dto) {
        return ResponseEntity.ok(jobApplicationService.applyForJob(dto));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplicationDto>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByJob(jobId));
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<JobApplicationDto>> getByCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByCandidate(candidateId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<JobApplicationDto> updateStatus(@PathVariable Long id,
                                                           @RequestParam String status) {
        return ResponseEntity.ok(jobApplicationService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        jobApplicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully");
    }
}
