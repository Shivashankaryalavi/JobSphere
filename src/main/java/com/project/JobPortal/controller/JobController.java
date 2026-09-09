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

import com.project.JobPortal.model.Job;
import com.project.JobPortal.service.JobService;

@RestController
public class JobController {

    private JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    
    @GetMapping("/jobs")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable int id) {
    	Optional<Job> job = jobService.getJobById(id);

        if (job.isPresent()) {
            return ResponseEntity.ok(job.get());
        }

        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/jobs")
    public Job addJob(@RequestBody Job job) {
    	return jobService.addJob(job);
    }
    @PutMapping("/jobs/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable int id, @RequestBody Job job){
    	Optional<Job> updatedJob = jobService.updateJob(id, job);
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