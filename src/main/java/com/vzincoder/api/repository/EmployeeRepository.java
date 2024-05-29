package com.vzincoder.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vzincoder.api.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

        Optional<Employee> findByCpf(String cpf);

        public boolean existsByEmail(String email);

        public boolean existsByCpf(String cpf);

}
