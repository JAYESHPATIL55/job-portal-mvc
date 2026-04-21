package com.jayesh.jobportal.controller;

import com.jayesh.jobportal.dto.JobDto;
import com.jayesh.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobDto>createJob(@RequestBody JobDto jobDto){
        return ResponseEntity.ok(jobService.createJob(jobDto));
    }

    @GetMapping
    public ResponseEntity<Page<JobDto>>getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
                                                   ){
        return ResponseEntity.ok(jobService.getAllJobs(page, size, sortBy, direction)

        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id){
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobDto>> searchByLocation(@RequestParam String location){
        return ResponseEntity.ok(jobService.searchJobsByLocation(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDto> updateJob(
            @PathVariable long id,
            @RequestBody JobDto jobDto){
        return ResponseEntity.ok(jobService.updateJob(id,jobDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteJob(@PathVariable Long id){
        jobService.deleteJob(id);
        return ResponseEntity.ok("Job Delete successfully");
    }

}
