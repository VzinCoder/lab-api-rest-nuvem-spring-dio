package com.vzincoder.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    
}
