package com.jayesh.jobportal.controller.mvc;

import com.jayesh.jobportal.dto.JobDto;
import com.jayesh.jobportal.service.CompanyService;
import com.jayesh.jobportal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobViewController {

    private final JobService jobService;
    private final CompanyService companyService;

    @GetMapping
    public String listJobs(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(defaultValue = "id") String sortBy,
                           @RequestParam(defaultValue = "asc") String direction,
                           Model model) {
        Page<JobDto> jobPage = jobService.getAllJobs(page, size, sortBy, direction);
        model.addAttribute("jobs", jobPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", jobPage.getTotalPages());
        model.addAttribute("totalItems", jobPage.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        return "jobs/list";
    }

    @GetMapping("/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "jobs/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("job", new JobDto());
        model.addAttribute("companies", companyService.getAllCompaniesForDropdown());
        return "jobs/form";
    }

    @PostMapping("/new")
    public String createJob(@Valid @ModelAttribute("job") JobDto jobDto,
                            BindingResult result, Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("companies", companyService.getAllCompaniesForDropdown());
            return "jobs/form";
        }
        jobService.createJob(jobDto);
        redirectAttributes.addFlashAttribute("successMessage", "Job created successfully!");
        return "redirect:/jobs";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        model.addAttribute("companies", companyService.getAllCompaniesForDropdown());
        return "jobs/form";
    }

    @PostMapping("/{id}/edit")
    public String updateJob(@PathVariable Long id,
                            @Valid @ModelAttribute("job") JobDto jobDto,
                            BindingResult result, Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("companies", companyService.getAllCompaniesForDropdown());
            return "jobs/form";
        }
        jobService.updateJob(id, jobDto);
        redirectAttributes.addFlashAttribute("successMessage", "Job updated successfully!");
        return "redirect:/jobs";
    }

    @PostMapping("/{id}/delete")
    public String deleteJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        jobService.deleteJob(id);
        redirectAttributes.addFlashAttribute("successMessage", "Job deleted successfully!");
        return "redirect:/jobs";
    }

    @GetMapping("/search")
    public String searchJobs(@RequestParam String location, Model model) {
        model.addAttribute("jobs", jobService.searchJobsByLocation(location));
        model.addAttribute("searchLocation", location);
        return "jobs/list";
    }
}
