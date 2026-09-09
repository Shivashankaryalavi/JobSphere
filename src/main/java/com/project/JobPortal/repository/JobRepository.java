package com.project.JobPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.JobPortal.model.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {

}