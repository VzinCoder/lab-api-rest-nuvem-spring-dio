package com.vzincoder.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
} 
