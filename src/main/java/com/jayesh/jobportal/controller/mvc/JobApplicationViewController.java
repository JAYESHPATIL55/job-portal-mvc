package com.jayesh.jobportal.controller.mvc;

import com.jayesh.jobportal.dto.JobApplicationDto;
import com.jayesh.jobportal.service.CandidateService;
import com.jayesh.jobportal.service.JobApplicationService;
import com.jayesh.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class JobApplicationViewController {

    private final JobApplicationService jobApplicationService;
    private final JobService jobService;
    private final CandidateService candidateService;

    @GetMapping
    public String listApplications(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {
        Page<JobApplicationDto> appPage = jobApplicationService.getAllApplications(page, size);
        model.addAttribute("applications", appPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appPage.getTotalPages());
        model.addAttribute("totalItems", appPage.getTotalElements());
        return "applications/list";
    }

    @GetMapping("/new")
    public String showApplyForm(Model model) {
        model.addAttribute("application", new JobApplicationDto());
        model.addAttribute("jobs", jobService.getAllJobsForDropdown());
        model.addAttribute("candidates", candidateService.getAllCandidatesForDropdown());
        return "applications/form";
    }

    @PostMapping("/new")
    public String applyForJob(@ModelAttribute("application") JobApplicationDto dto,
                              RedirectAttributes redirectAttributes) {
        jobApplicationService.applyForJob(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Application submitted successfully!");
        return "redirect:/applications";
    }

    @PostMapping("/{id}/delete")
    public String deleteApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        jobApplicationService.deleteApplication(id);
        redirectAttributes.addFlashAttribute("successMessage", "Application removed successfully!");
        return "redirect:/applications";
    }
}
