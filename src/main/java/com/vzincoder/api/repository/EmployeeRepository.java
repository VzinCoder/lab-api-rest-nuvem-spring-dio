package com.vzincoder.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vzincoder.api.domain.Employee;
import com.vzincoder.api.dto.EmployeeDTO;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

        @Query("SELECT new com.vzincoder.api.dto.EmployeeDTO(e.id, e.email, e.password, e.name, e.cpf, e.salary, COALESCE(SUM(CASE WHEN MONTH(r.dateCheckOut) = MONTH(CURRENT_DATE()) AND YEAR(r.dateCheckOut) = YEAR(CURRENT_DATE()) THEN r.price * 0.05 ELSE 0 END), 0), COALESCE(COUNT(CASE WHEN r.status = com.vzincoder.api.domain.ReserveStatus.CHECKED_OUT AND MONTH(r.dateCheckOut) = MONTH(CURRENT_DATE()) AND YEAR(r.dateCheckOut) = YEAR(CURRENT_DATE()) THEN r.id END), 0)) "
                        +
                        "FROM Employee e LEFT JOIN Reserve r ON e.id = r.employee.id WHERE e.id = :id " +
                        "GROUP BY e.id")
        Optional<EmployeeDTO> findById(@Param("id") int id);

        @Query("SELECT new com.vzincoder.api.dto.EmployeeDTO(e.id, e.email, e.password, e.name, e.cpf, e.salary, COALESCE(SUM(CASE WHEN MONTH(r.dateCheckOut) = MONTH(CURRENT_DATE()) AND YEAR(r.dateCheckOut) = YEAR(CURRENT_DATE()) THEN r.price * 0.05 ELSE 0 END), 0), COALESCE(COUNT(CASE WHEN r.status = com.vzincoder.api.domain.ReserveStatus.CHECKED_OUT AND MONTH(r.dateCheckOut) = MONTH(CURRENT_DATE()) AND YEAR(r.dateCheckOut) = YEAR(CURRENT_DATE()) THEN r.id END), 0)) "
                        +
                        "FROM Employee e LEFT JOIN Reserve r ON e.id = r.employee.id WHERE e.email = :email " +
                        "GROUP BY e.id")
        Optional<EmployeeDTO> findByEmail(@Param("email") String email);

        @Query("SELECT new com.vzincoder.api.dto.EmployeeDTO(e.id, e.email, e.password, e.name, e.cpf, e.salary, COALESCE(SUM(CASE WHEN MONTH(r.dateCheckOut) = MONTH(CURRENT_DATE()) AND YEAR(r.dateCheckOut) = YEAR(CURRENT_DATE()) THEN r.price * 0.05 ELSE 0 END), 0), COALESCE(COUNT(CASE WHEN r.status = com.vzincoder.api.domain.ReserveStatus.CHECKED_OUT AND MONTH(r.dateCheckOut) = MONTH(CURRENT_DATE()) AND YEAR(r.dateCheckOut) = YEAR(CURRENT_DATE()) THEN r.id END), 0)) "
                        +
                        "FROM Employee e LEFT JOIN Reserve r ON e.id = r.employee.id WHERE e.cpf = :cpf " +
                        "GROUP BY e.id")
        Optional<EmployeeDTO> findByCpf(@Param("cpf") String cpf);

        @Query("SELECT new com.vzincoder.api.dto.EmployeeDTO(e.id, e.email, e.password, e.name, e.cpf, e.salary, COALESCE(SUM(CASE WHEN MONTH(r.dateCheckOut) = MONTH(CURRENT_DATE()) AND YEAR(r.dateCheckOut) = YEAR(CURRENT_DATE()) THEN r.price * 0.05 ELSE 0 END), 0), COALESCE(COUNT(CASE WHEN r.status = com.vzincoder.api.domain.ReserveStatus.CHECKED_OUT AND MONTH(r.dateCheckOut) = MONTH(CURRENT_DATE()) AND YEAR(r.dateCheckOut) = YEAR(CURRENT_DATE()) THEN r.id END), 0)) "
                        +
                        "FROM Employee e LEFT JOIN Reserve r ON e.id = r.employee.id " +
                        "GROUP BY e.id, e.email, e.password, e.name, e.cpf, e.salary")
        List<EmployeeDTO> findAllEmployees();

        public boolean existsByEmail(String email);

        public boolean existsByCpf(String cpf);

}
