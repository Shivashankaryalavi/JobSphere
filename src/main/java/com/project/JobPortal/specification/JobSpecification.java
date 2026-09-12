package com.project.JobPortal.specification;

import org.springframework.data.jpa.domain.Specification;

import com.project.JobPortal.model.Job;

public class JobSpecification {

    public static Specification<Job> hasTitle(String title)
    {

        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like( criteriaBuilder.lower(root.get("title")),"%" + title.toLowerCase() + "%" );
    }
    
    
    public static Specification<Job> hasLocation(String location) 
    {

        return (root, query, criteriaBuilder) ->
           criteriaBuilder.equal(criteriaBuilder.lower(root.get("location")), location.toLowerCase());
    }
    
    
    public static Specification<Job> hasMinimumSalary(Double minSalary)
    {

        return (root, query, criteriaBuilder) ->
           criteriaBuilder.greaterThanOrEqualTo(root.get("salary"),minSalary );
    }
    
    public static Specification<Job> hasMaximumSalary(Double maxSalary) 
    {

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo( root.get("salary"), maxSalary);
    }
}