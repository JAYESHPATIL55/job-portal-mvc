package com.jayesh.jobportal.controller.mvc;

import com.jayesh.jobportal.dto.CandidateDto;
import com.jayesh.jobportal.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateViewController {

    private final CandidateService candidateService;

    @GetMapping
    public String listCandidates(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 @RequestParam(defaultValue = "asc") String direction,
                                 Model model) {
        Page<CandidateDto> candidatePage = candidateService.getAllCandidates(page, size, sortBy, direction);
        model.addAttribute("candidates", candidatePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", candidatePage.getTotalPages());
        model.addAttribute("totalItems", candidatePage.getTotalElements());
        model.addAttribute("size", size);
        return "candidates/list";
    }

    @GetMapping("/{id}")
    public String viewCandidate(@PathVariable Long id, Model model) {
        model.addAttribute("candidate", candidateService.getCandidateById(id));
        return "candidates/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("candidate", new CandidateDto());
        return "candidates/form";
    }

    @PostMapping("/new")
    public String createCandidate(@Valid @ModelAttribute("candidate") CandidateDto candidateDto,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "candidates/form";
        candidateService.createCandiate(candidateDto);
        redirectAttributes.addFlashAttribute("successMessage", "Candidate registered successfully!");
        return "redirect:/candidates";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("candidate", candidateService.getCandidateById(id));
        return "candidates/form";
    }

    @PostMapping("/{id}/edit")
    public String updateCandidate(@PathVariable Long id,
                                  @Valid @ModelAttribute("candidate") CandidateDto candidateDto,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "candidates/form";
        candidateService.updateCandidate(id, candidateDto);
        redirectAttributes.addFlashAttribute("successMessage", "Candidate updated successfully!");
        return "redirect:/candidates";
    }

    @PostMapping("/{id}/delete")
    public String deleteCandidate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        candidateService.deleteCandidate(id);
        redirectAttributes.addFlashAttribute("successMessage", "Candidate deleted successfully!");
        return "redirect:/candidates";
    }
}
