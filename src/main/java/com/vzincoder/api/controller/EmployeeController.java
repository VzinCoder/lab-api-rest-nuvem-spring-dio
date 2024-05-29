package com.vzincoder.api.controller;

import com.vzincoder.api.domain.Employee;
import com.vzincoder.api.dto.EmployeeCreateDTO;
import com.vzincoder.api.dto.EmployeeDTO;
import com.vzincoder.api.dto.MessageResponseDTO;
import com.vzincoder.api.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/cpf/{cpf}/{month}/{year}")
    public ResponseEntity<EmployeeDTO> getEmployeeByCpfAndMonthAndYear(@PathVariable String cpf,@PathVariable int month,@PathVariable int year) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByCpfAndMonthAndYear(cpf, month, year);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<EmployeeDTO> getEmployeeByCpf(@PathVariable String cpf) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByCpf(cpf);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @GetMapping("/month/{month}/year/{year}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesByMonthAndYear(@PathVariable int month,
            @PathVariable int year) {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployeeByMonthAndYear(month, year);
        return ResponseEntity.ok(employeeDTOList);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeDTO createdEmployeeDTO = employeeService.createEmployee(employeeCreateDTO);
        return new ResponseEntity<>(createdEmployeeDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<MessageResponseDTO> deleteEmployeeByCpf(@PathVariable String cpf) {
        employeeService.deleteEmployeeByCpf(cpf);
        return ResponseEntity.ok(new MessageResponseDTO("Employee with CPF " + cpf + " deleted successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(new MessageResponseDTO("Employee with ID " + id + " deleted successfully"));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeCreateDTO employeeCreateDTO,
            @PathVariable int id) {
        Employee updateEmployee = new Employee();
        
        updateEmployee.setId(id);
        updateEmployee.setCpf(employeeCreateDTO.getCpf());
        updateEmployee.setEmail(employeeCreateDTO.getEmail());
        updateEmployee.setName(employeeCreateDTO.getName());
        updateEmployee.setPassword(employeeCreateDTO.getPassword());
        updateEmployee.setSalary(employeeCreateDTO.getSalary());


        EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(updateEmployee);
        return ResponseEntity.ok(updatedEmployeeDTO);
    }
}
