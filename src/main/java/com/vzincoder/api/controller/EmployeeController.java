package com.vzincoder.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vzincoder.api.domain.Employee;
import com.vzincoder.api.dto.EmployeeCreateDTO;
import com.vzincoder.api.dto.EmployeeDTO;
import com.vzincoder.api.dto.MessageResponseDTO;
import com.vzincoder.api.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@PathVariable String email) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmail(email));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<EmployeeDTO> getEmployeeByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(employeeService.getEmployeeByCpf(cpf));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeesDTO = employeeService.getAllEmployee();
        return ResponseEntity.ok(employeesDTO);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable int id,@Valid @RequestBody  EmployeeCreateDTO employeeCreateDTO) {
        Employee updateEmployee = new Employee();
        updateEmployee.setId(id);
        updateEmployee.setEmail(employeeCreateDTO.getEmail());
        updateEmployee.setPassword(employeeCreateDTO.getPassword());
        updateEmployee.setName(employeeCreateDTO.getName());
        updateEmployee.setCpf(employeeCreateDTO.getCpf());
        updateEmployee.setSalary(employeeCreateDTO.getSalary());
        EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(updateEmployee);
        return ResponseEntity.ok(updatedEmployeeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(new MessageResponseDTO("Employee with ID " + id + " deleted successfully"));
    }


    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<MessageResponseDTO> deleteEmployeeByCpf(@PathVariable String cpf) {
        employeeService.deleteEmployeeByCpf(cpf);
        return ResponseEntity.ok(new MessageResponseDTO("Employee with Cpf " + cpf + " deleted successfully"));
    }

}
