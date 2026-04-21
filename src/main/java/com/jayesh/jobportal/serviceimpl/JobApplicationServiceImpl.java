package com.jayesh.jobportal.serviceimpl;

import com.jayesh.jobportal.dto.JobApplicationDto;
import com.jayesh.jobportal.entity.Candidate;
import com.jayesh.jobportal.entity.Job;
import com.jayesh.jobportal.entity.JobApplication;
import com.jayesh.jobportal.exception.JobApplicationException;
import com.jayesh.jobportal.repository.CandidateRepository;
import com.jayesh.jobportal.repository.JobApplicationRepository;
import com.jayesh.jobportal.repository.JobRepository;
import com.jayesh.jobportal.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public JobApplicationDto applyForJob(JobApplicationDto jobApplicationDto) {
        Job job = jobRepository.findById(jobApplicationDto.getJobId())
                .orElseThrow(() -> new JobApplicationException("Job not found with id: " + jobApplicationDto.getJobId()));
        Candidate candidate = candidateRepository.findById(jobApplicationDto.getCandidateId())
                .orElseThrow(() -> new JobApplicationException("Candidate not found with id: " + jobApplicationDto.getCandidateId()));
        JobApplication application = JobApplication.builder()
                .job(job)
                .candidate(candidate)
                .appliedDate(LocalDate.now())
                .status("APPLIED")
                .build();
        return mapToDto(jobApplicationRepository.save(application));
    }

    @Override
    public List<JobApplicationDto> getApplicationsByJob(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<JobApplicationDto> getApplicationsByCandidate(Long candidateId) {
        return jobApplicationRepository.findByCandidateId(candidateId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public JobApplicationDto updateStatus(Long id, String status) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationException("Application not found with id: " + id));
        application.setStatus(status);
        return mapToDto(jobApplicationRepository.save(application));
    }

    @Override
    public void deleteApplication(Long id) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationException("Application not found with id: " + id));
        jobApplicationRepository.delete(application);
    }

    @Override
    public Page<JobApplicationDto> getAllApplications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appliedDate").descending());
        return jobApplicationRepository.findAll(pageable).map(this::mapToDto);
    }

    private JobApplicationDto mapToDto(JobApplication app) {
        return JobApplicationDto.builder()
                .id(app.getId())
                .appliedDate(app.getAppliedDate())
                .status(app.getStatus())
                .jobId(app.getJob().getId())
                .jobTitle(app.getJob().getTitle())
                .candidateId(app.getCandidate().getId())
                .candidateName(app.getCandidate().getName())
                .build();
    }
}
