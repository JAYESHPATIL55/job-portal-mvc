package com.jayesh.jobportal.controller.mvc;

import com.jayesh.jobportal.dto.CompanyDto;
import com.jayesh.jobportal.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyViewController {

    private final CompanyService companyService;

    @GetMapping
    public String listCompanies(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy,
                                @RequestParam(defaultValue = "asc") String direction,
                                Model model) {
        Page<CompanyDto> companyPage = companyService.getAllCompanies(page, size, sortBy, direction);
        model.addAttribute("companies", companyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("totalItems", companyPage.getTotalElements());
        return "companies/list";
    }

    @GetMapping("/{id}")
    public String viewCompany(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.getCompanyById(id));
        return "companies/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("company", new CompanyDto());
        return "companies/form";
    }

    @PostMapping("/new")
    public String createCompany(@Valid @ModelAttribute("company") CompanyDto companyDto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "companies/form";
        companyService.createCompany(companyDto);
        redirectAttributes.addFlashAttribute("successMessage", "Company created successfully!");
        return "redirect:/companies";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.getCompanyById(id));
        return "companies/form";
    }

    @PostMapping("/{id}/edit")
    public String updateCompany(@PathVariable Long id,
                                @Valid @ModelAttribute("company") CompanyDto companyDto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "companies/form";
        companyService.updateCompany(id, companyDto);
        redirectAttributes.addFlashAttribute("successMessage", "Company updated successfully!");
        return "redirect:/companies";
    }

    @PostMapping("/{id}/delete")
    public String deleteCompany(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        companyService.deleteCompany(id);
        redirectAttributes.addFlashAttribute("successMessage", "Company deleted successfully!");
        return "redirect:/companies";
    }
}
