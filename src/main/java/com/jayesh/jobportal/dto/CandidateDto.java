package com.jayesh.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDto {

    private Long id;
    @NotBlank(message = "Candidate name is required")
    private String name;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    private String skills;
    @Min(value = 0,message = "Experience cannot be negative")
    private Integer experience;


}
