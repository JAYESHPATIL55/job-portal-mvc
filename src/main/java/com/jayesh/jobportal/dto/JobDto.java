package com.jayesh.jobportal.dto;


import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDto {

    private Long id;
    private String title;
    private String description;
    private Double salary;
    private String location;

    private Long companyId;
    private String companyName;
}
