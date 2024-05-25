package com.vzincoder.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve,Integer>{
   
} 
