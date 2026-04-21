package com.jayesh.jobportal.service;

import com.jayesh.jobportal.dto.JobApplicationDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface JobApplicationService {
    JobApplicationDto applyForJob(JobApplicationDto jobApplicationDto);
    List<JobApplicationDto> getApplicationsByJob(Long jobId);
    List<JobApplicationDto> getApplicationsByCandidate(Long candidateId);
    JobApplicationDto updateStatus(Long id, String status);
    void deleteApplication(Long id);
    /** Paginated list for MVC views */
    Page<JobApplicationDto> getAllApplications(int page, int size);
}
