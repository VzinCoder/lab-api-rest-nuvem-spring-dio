package com.vzincoder.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
}
