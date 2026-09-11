package com.project.JobPortal.controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.project.JobPortal.dto.JobRequestDTO;
import com.project.JobPortal.dto.JobUpdateDTO;
import com.project.JobPortal.dto.JobResponseDTO;



import com.project.JobPortal.service.JobService;

import jakarta.validation.Valid;

@RestController
public class JobController {

    private JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    
    @GetMapping("/jobs")
    public List<JobResponseDTO> getAllJobs() {
        return jobService.getAllJobs();
    }
    
    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobResponseDTO> getJobById(@PathVariable int id) {
    	Optional<JobResponseDTO> job = jobService.getJobById(id);

        if (job.isPresent()) {
            return ResponseEntity.ok(job.get());
        }

        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/jobs")
    public JobResponseDTO addJob(@RequestBody   @Valid  JobRequestDTO jobDTO) {
        return jobService.addJob(jobDTO);
    }
    
    
    @PutMapping("/jobs/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(@PathVariable int id, @RequestBody @Valid JobUpdateDTO jobDTO){
    	
    	Optional<JobResponseDTO> updatedJob = jobService.updateJob(id, jobDTO);
    	
    	if (updatedJob.isPresent()) {
    	    return ResponseEntity.ok(updatedJob.get());
    	}

    	return ResponseEntity.notFound().build();
    	
    }
    @DeleteMapping("/jobs/{id}")
    public void deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
    }

  
}