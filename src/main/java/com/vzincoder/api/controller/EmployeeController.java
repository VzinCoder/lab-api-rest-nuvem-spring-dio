package com.vzincoder.api.controller;

import com.vzincoder.api.domain.Employee;
import com.vzincoder.api.dto.EmployeeCreateDTO;
import com.vzincoder.api.dto.EmployeeDTO;
import com.vzincoder.api.dto.MessageResponseDTO;
import com.vzincoder.api.service.EmployeeService;
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
    public ResponseEntity<EmployeeDTO> getEmployeeByCpfAndMonthAndYear(@PathVariable String cpf,
            @PathVariable int month,
            @PathVariable int year) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByCpfAndMonthAndYear(cpf, month, year);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/{month}/{year}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesByMonthAndYear(@PathVariable int month,
            @PathVariable int year) {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployeesByMonthAndYear(month, year);
        return ResponseEntity.ok(employeeDTOList);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeDTO createdEmployeeDTO = employeeService.createEmployee(employeeCreateDTO);
        return new ResponseEntity<>(createdEmployeeDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<MessageResponseDTO> deleteEmployeeByCpf(@PathVariable String cpf) {
        employeeService.deleteEmployeeByCpf(cpf);
        return ResponseEntity.ok(new MessageResponseDTO("Employee with ID " + cpf + " deleted successfully"));
    }

    @PutMapping("{cpf}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeCreateDTO employeeCreateDTO,@PathVariable String cpf) {
        Employee employee = new Employee();
        employee.setCpf(cpf);
        employee.setEmail(employeeCreateDTO.getEmail());
        employee.setName(employeeCreateDTO.getName());
        employee.setPassword(employeeCreateDTO.getPassword());
        employee.setSalary(employeeCreateDTO.getSalary());
        EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployeeDTO);
    }
}
