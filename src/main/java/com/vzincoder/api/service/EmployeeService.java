package com.vzincoder.api.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vzincoder.api.domain.Employee;
import com.vzincoder.api.domain.Reserve;
import com.vzincoder.api.domain.ReserveStatus;
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
        Optional<Employee> employeeFound = employeeRepository.findById(id);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("Employee not found");
        }

        LocalDate currentDate = LocalDate.now();
        return convertToDTO(employeeFound.get(), currentDate.getMonthValue(), currentDate.getYear());
    }

    public EmployeeDTO getEmployeeByCpf(String cpf) {
        Optional<Employee> employeeFound = employeeRepository.findByCpf(cpf);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("Employee not found");
        }

        LocalDate currentDate = LocalDate.now();
        return convertToDTO(employeeFound.get(), currentDate.getMonthValue(), currentDate.getYear());
    }

    public EmployeeDTO getEmployeeByCpfAndMonthAndYear(String cpf, int month, int year) {
        Optional<Employee> employeeFound = employeeRepository.findByCpf(cpf);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("Employee not found");
        }

        Employee employee = employeeFound.get();
        LocalDate employeeStartDate = employee.getDate();

        YearMonth currentYearMonth = YearMonth.now();
        YearMonth requestedYearMonth = YearMonth.of(year, month);

        if (requestedYearMonth.isAfter(currentYearMonth)) {
            throw new EntityNotFoundException("Cannot search for employee in future months");
        }

        YearMonth employeeStartYearMonth = YearMonth.of(employeeStartDate.getYear(), employeeStartDate.getMonth());
        if (requestedYearMonth.isBefore(employeeStartYearMonth)) {
            throw new EntityNotFoundException("Employee was not employed in the specified month and year");
        }

        return convertToDTO(employeeFound.get(), month, year);
    }

    public List<EmployeeDTO> getAllEmployeeByMonthAndYear(int month, int year) {
        List<Employee> employeesList = employeeRepository.findAll();

        YearMonth currentYearMonth = YearMonth.now();
        YearMonth requestedYearMonth = YearMonth.of(year, month);

        if (requestedYearMonth.isAfter(currentYearMonth)) {
            throw new EntityNotFoundException("Cannot search for employee in future months");
        }

        return employeesList.stream().filter(employee -> {
            LocalDate employeeStartDate = employee.getDate();
            YearMonth employeeStartYearMonth = YearMonth.of(employeeStartDate.getYear(), employeeStartDate.getMonth());
            return requestedYearMonth.equals(employeeStartYearMonth) || requestedYearMonth.isAfter(employeeStartYearMonth);
        }).map(employee -> convertToDTO(employee, month, year)).toList();
    }

    public List<EmployeeDTO> getAllEmployee() {
        LocalDate currentDate = LocalDate.now();
        return employeeRepository.findAll().stream()
                .map(employee -> convertToDTO(employee, currentDate.getMonthValue(), currentDate.getYear()))
                .toList();
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
            LocalDate currentDate = LocalDate.now();
            return convertToDTO(employeeRepository.save(newEmployee), currentDate.getMonthValue(),
                    currentDate.getYear());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DataIntegrityException("Failed to create employee");
        }
    }

    public void deleteEmployeeById(int id) {
        Optional<Employee> employeeFound = employeeRepository.findById(id);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("Employee not found");
        }

        employeeRepository.delete(employeeFound.get());
    }

    public void deleteEmployeeByCpf(String cpf) {
        Optional<Employee> employeeFound = employeeRepository.findByCpf(cpf);

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("Employee not found");
        }

        employeeRepository.delete(employeeFound.get());
    }


    public EmployeeDTO updateEmployee(Employee employee) {
        Optional<Employee> employeeFound = employeeRepository.findById(employee.getId());

        if (employeeFound.isEmpty()) {
            throw new EntityNotFoundException("Employee not found");
        }

        Employee existingEmployee = employeeFound.get();

        if (!existingEmployee.getEmail().equals(employee.getEmail()) &&
                employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DataIntegrityException("This email is already being used!");
        }

        if (!existingEmployee.getCpf().equals(employee.getCpf()) &&
                employeeRepository.existsByCpf(employee.getCpf())) {
            throw new DataIntegrityException("This CPF is already being used!");
        }

        try {
            existingEmployee.setCpf(employee.getCpf());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setName(employee.getName());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setPassword(employee.getPassword());
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            
            LocalDate currentDate = LocalDate.now();
            return convertToDTO(updatedEmployee, currentDate.getMonthValue(), currentDate.getYear());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DataIntegrityException("Failed to update employee");
        }
    }

    public EmployeeDTO convertToDTO(Employee employee, int month, int year) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setCpf(employee.getCpf());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setDate(employee.getDate());

        List<Reserve> reserveCheckedMonthList = employee.getReserves().stream().filter(reserve -> {
            boolean isReserveChekedOut = reserve.getStatus().equals(ReserveStatus.FINALIZED);
            boolean isEqualMonth = reserve.getDateCheckIn().getMonthValue() == month;
            boolean isEqualYear = reserve.getDateCheckIn().getYear() == year;
            if (isReserveChekedOut && isEqualYear && isEqualMonth) {
                return true;
            }
            return false;
        }).toList();

        double bonus = reserveCheckedMonthList
                .stream()
                .mapToDouble(reserve -> reserve.getPrice())
                .reduce(0, (acc, price) -> acc += price * 0.05);

        employeeDTO.setQtyReserve(reserveCheckedMonthList.size());
        employeeDTO.setBonus(bonus);
        employeeDTO.setTotal(employeeDTO.getBonus() + employeeDTO.getSalary());

        return employeeDTO;
    }

    private Employee convertEmployeeCreateDTOToEmployee(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = new Employee();
        employee.setCpf(employeeCreateDTO.getCpf());
        employee.setEmail(employeeCreateDTO.getEmail());
        employee.setName(employeeCreateDTO.getName());
        employee.setSalary(employeeCreateDTO.getSalary());
        employee.setPassword(employeeCreateDTO.getPassword());
        return employee;
    }

}
