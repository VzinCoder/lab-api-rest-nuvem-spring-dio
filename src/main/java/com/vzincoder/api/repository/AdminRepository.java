package com.vzincoder.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Admin;
import java.util.Optional;



@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    public Optional<Admin> findByEmail(String email);
} 
