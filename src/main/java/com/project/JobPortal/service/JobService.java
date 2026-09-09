package com.project.JobPortal.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.project.JobPortal.model.Job;
import org.springframework.stereotype.Service;

import com.project.JobPortal.repository.JobRepository;
import java.util.Optional;


@Service
public class JobService {

	private JobRepository jobRepository;

	@Autowired
	public JobService(JobRepository jobRepository) {
	    this.jobRepository = jobRepository;
	}
	
	
	public Optional<Job> getJobById(int id) {
	    return jobRepository.findById(id);
	}
	public List<Job> getAllJobs() {
	    return jobRepository.findAll();
	}
	public Job addJob(Job job) {
	    return jobRepository.save(job);
	}
	
	public Optional<Job> updateJob(int id, Job job) {

	    Optional<Job> existingJob = jobRepository.findById(id);

	    if (existingJob.isPresent()) {

	        Job oldJob = existingJob.get();

	        oldJob.setTitle(job.getTitle());
	        oldJob.setLocation(job.getLocation());
	        oldJob.setSalary(job.getSalary());

	        return Optional.of(jobRepository.save(oldJob));
	    }

	    return Optional.empty();
	}
	public void deleteJob(int id) {
	    jobRepository.deleteById(id);
	}
}
