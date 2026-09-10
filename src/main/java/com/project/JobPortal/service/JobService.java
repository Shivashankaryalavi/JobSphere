package com.project.JobPortal.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.JobPortal.dto.JobRequestDTO;

import java.util.ArrayList;
import java.util.List;

import com.project.JobPortal.model.Job;
import org.springframework.stereotype.Service;

import com.project.JobPortal.repository.JobRepository;
import java.util.Optional;
import com.project.JobPortal.dto.JobUpdateDTO;
import com.project.JobPortal.dto.JobResponseDTO;


@Service
public class JobService {

	private JobRepository jobRepository;

	@Autowired
	public JobService(JobRepository jobRepository) {
	    this.jobRepository = jobRepository;
	}
	
	
	public Optional<JobResponseDTO> getJobById(int id) {
		Optional<Job> job = jobRepository.findById(id);
		if (job.isPresent()) {
			
			JobResponseDTO responseDTO = new JobResponseDTO();
			
			responseDTO.setId(job.get().getId());
			responseDTO.setTitle(job.get().getTitle());
			responseDTO.setLocation(job.get().getLocation());
			responseDTO.setSalary(job.get().getSalary());
			
			return Optional.of(responseDTO);
		}
		return Optional.empty();
	}
	public List<JobResponseDTO> getAllJobs() {
		
		List<Job> jobs = jobRepository.findAll();
		List<JobResponseDTO> responseList = new ArrayList<>();
		
		for (Job job : jobs) {
			  
			JobResponseDTO responseDTO = new JobResponseDTO();
			
			  responseDTO.setId(job.getId());
			  responseDTO.setTitle(job.getTitle());
			  responseDTO.setLocation(job.getLocation());
		      responseDTO.setSalary(job.getSalary());
		      responseList.add(responseDTO);
	    }
		return responseList;
	}
	
	public JobResponseDTO addJob(JobRequestDTO jobDTO) {
		Job job = new Job();

		job.setTitle(jobDTO.getTitle());
		job.setLocation(jobDTO.getLocation());
		job.setSalary(jobDTO.getSalary());
		
		Job savedJob = jobRepository.save(job);
		
		JobResponseDTO responseDTO = new JobResponseDTO();
		
		responseDTO.setId(savedJob.getId());
		responseDTO.setTitle(savedJob.getTitle());
		responseDTO.setLocation(savedJob.getLocation());
		responseDTO.setSalary(savedJob.getSalary());
		
		return responseDTO;
		
	
	}
	
	public Optional<JobResponseDTO> updateJob(int id, JobUpdateDTO jobDTO) {

	    Optional<Job> existingJob = jobRepository.findById(id);

	    if (existingJob.isPresent()) {

	        Job oldJob = existingJob.get();

	        oldJob.setTitle(jobDTO.getTitle());
	        oldJob.setLocation(jobDTO.getLocation());
	        oldJob.setSalary(jobDTO.getSalary());
	        
	        JobResponseDTO responseDTO = new JobResponseDTO();
	        
	        responseDTO.setId(oldJob.getId());
	        responseDTO.setTitle(oldJob.getTitle());
	        responseDTO.setLocation(oldJob.getLocation());
	        responseDTO.setSalary(oldJob.getSalary());

	        jobRepository.save(oldJob);

	        return Optional.of(responseDTO);
	    }

	    return Optional.empty();
	}
	public boolean deleteJob(int id) {

	    if (jobRepository.existsById(id)) {
	        jobRepository.deleteById(id);
	        return true;
	    }

	    return false;
	}
}
