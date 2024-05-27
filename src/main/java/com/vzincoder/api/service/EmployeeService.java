package com.vzincoder.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vzincoder.api.domain.Employee;
import com.vzincoder.api.dto.EmployeeCreateDTO;
import com.vzincoder.api.dto.EmployeeDTO;
import com.vzincoder.api.exception.DataIntegrityException;
import com.vzincoder.api.exception.EntityNotFoundException;
import com.vzincoder.api.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO getEmployeeById(int id) {
        Optional<EmployeeDTO> employeeFound = employeeRepository.findById(id);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("employee not afound");
        }

        return employeeFound.get();
    }

    public EmployeeDTO getEmployeeByEmail(String email) {
        Optional<EmployeeDTO> employeeFound = employeeRepository.findByEmail(email);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("employee not afound");
        }

        return employeeFound.get();
    }

    public EmployeeDTO getEmployeeByCpf(String Cpf) {
        Optional<EmployeeDTO> employeeFound = employeeRepository.findByCpf(Cpf);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("employee not afound");
        }

        return employeeFound.get();
    }

    public List<EmployeeDTO> getAllEmployee() {
        return employeeRepository.findAllEmployees();
    }

    public EmployeeDTO createEmployee(EmployeeCreateDTO employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DataIntegrityException("This email is already being used!");
        }
    
        if (employeeRepository.existsByCpf(employee.getCpf())) {
            throw new DataIntegrityException("This CPF is already being used!");
        }
    
        try {
            Employee newEmployee = convertEmployeeCreateDTOToEmployee(employee);
            return convertToDTO(employeeRepository.save(newEmployee));
        } catch (Exception e) {
            throw new DataIntegrityException("Failed to create employee");
        }
    }

    public void deleteEmployeeById(int id) {
        Optional<EmployeeDTO> employeeFound = employeeRepository.findById(id);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("employee not afound");
        }

        employeeRepository.delete(convertEmployeeDTOToEmployee(employeeFound.get()));
    }

    public void deleteEmployeeByCpf(String cpf) {
        Optional<EmployeeDTO> employeeFound = employeeRepository.findByCpf(cpf);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("employee not afound");
        }

        employeeRepository.delete(convertEmployeeDTOToEmployee(employeeFound.get()));
    }

    public EmployeeDTO updateEmployee(Employee employee) {
        Optional<EmployeeDTO> employeeFound = employeeRepository.findById(employee.getId());
    
        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("Employee not found");
        }
    
        EmployeeDTO existingEmployee = employeeFound.get();

        if (!existingEmployee.getEmail().equals(employee.getEmail()) &&
                employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DataIntegrityException("This email is already being used!");
        }
    
        if (!existingEmployee.getCpf().equals(employee.getCpf()) &&
                employeeRepository.existsByCpf(employee.getCpf())) {
            throw new DataIntegrityException("This CPF is already being used!");
        }
    
        try {
            Employee updatedEmployee = employeeRepository.save(employee);
            return convertToDTO(updatedEmployee);
    
        } catch (Exception e) {
            throw new DataIntegrityException("Failed to update employee");
        }
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setCpf(employee.getCpf());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPassword(employeeDTO.getPassword());
        employee.setCpf(employeeDTO.getCpf());
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        return employee;
    }

    private Employee convertEmployeeCreateDTOToEmployee(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = new Employee();
        employee.setCpf(employeeCreateDTO.getCpf());
        employee.setEmail(employeeCreateDTO.getEmail());
        employee.setName(employeeCreateDTO.getName());
        employee.setPassword(employeeCreateDTO.getName());
        employee.setSalary(employeeCreateDTO.getSalary());
        return employee;
    }

}
