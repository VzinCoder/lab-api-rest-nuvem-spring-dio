package com.vzincoder.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vzincoder.api.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


        @Query("SELECT e FROM Employee e JOIN e.reserves r WHERE e.cpf = :cpf AND FUNCTION('MONTH', r.dateCheckIn) = :month AND FUNCTION('YEAR', r.dateCheckIn) = :year")
        Optional<Employee> findByCpfAndMonthAndYear(@Param("cpf") String cpf, @Param("month") int month,
                        @Param("year") int year);

        @Query("SELECT DISTINCT e FROM Employee e JOIN e.reserves r WHERE FUNCTION('MONTH', r.dateCheckIn) = :month AND FUNCTION('YEAR', r.dateCheckIn) = :year")
        List<Employee> findAllByMonthAndYear(@Param("month") int month, @Param("year") int year);

        Optional<Employee> findByCpf(String cpf);

        public boolean existsByEmail(String email);

        public boolean existsByCpf(String cpf);

}
