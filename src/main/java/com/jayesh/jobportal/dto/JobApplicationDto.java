package com.jayesh.jobportal.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationDto {

    private Long id;
    private LocalDate appliedDate;
    private String status;

    private Long jobId;
    private String jobTitle;

    private Long candidateId;
    private String candidateName;




}
