package com.crm.service;

import com.crm.entity.Employee;
import com.crm.exception.ResourceNotFound;
import com.crm.payload.EmployeeDto;
import com.crm.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    //create a constructor based on dependency injection
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto addEmployee(EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        Employee emp = employeeRepository.save(employee);
        EmployeeDto employeeDto = mapToDto(emp);
        //employeeDto.setDate(new Date());
        return employeeDto;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        employee.setId(id);
        Employee updateEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDto = mapToDto(updateEmployee);
        return employeeDto;
    }

    public List<EmployeeDto> getEmployee(int PageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(PageNo, pageSize, sort);
        Page<Employee> all = employeeRepository.findAll(page);
        List<Employee> employees = all.getContent();
        List<EmployeeDto> dto = employees.stream().map(e->mapToDto(e)).collect(Collectors.toList());
        return dto;


    }

    EmployeeDto mapToDto(Employee employee) {
        EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);

        return dto;
    }

    Employee mapToEntity(EmployeeDto dto) {
        Employee emp = modelMapper.map(dto, Employee.class);

        return emp;
    }

    public EmployeeDto getEmployeeById(long empId) {
        Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new ResourceNotFound("Record not found with id : "+empId));
        EmployeeDto dto = mapToDto(employee);
        return dto;
    }
}