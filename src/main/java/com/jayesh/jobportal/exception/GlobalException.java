package com.jayesh.jobportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    /* ---- REST API Handlers ---- */

    @ExceptionHandler(JobException.class)
    public Object handleJobException(JobException ex,
            jakarta.servlet.http.HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
        return errorView(ex.getMessage());
    }

    @ExceptionHandler(CandidateException.class)
    public Object handleCandidateException(CandidateException ex,
            jakarta.servlet.http.HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
        return errorView(ex.getMessage());
    }

    @ExceptionHandler(CompanyException.class)
    public Object handleCompanyException(CompanyException ex,
            jakarta.servlet.http.HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
        return errorView(ex.getMessage());
    }

    @ExceptionHandler(JobApplicationException.class)
    public Object handleJobApplicationException(JobApplicationException ex,
            jakarta.servlet.http.HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
        return errorView(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public Object handleGeneral(Exception ex,
            jakarta.servlet.http.HttpServletRequest request) {
        if (isApiRequest(request)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred: " + ex.getMessage()));
        }
        return errorView("An unexpected error occurred: " + ex.getMessage());
    }

    private boolean isApiRequest(jakarta.servlet.http.HttpServletRequest request) {
        String uri = request.getRequestURI();
        String accept = request.getHeader("Accept");
        return uri.startsWith("/api/") ||
               (accept != null && accept.contains("application/json"));
    }

    private ModelAndView errorView(String message) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", message);
        return mav;
    }
}
