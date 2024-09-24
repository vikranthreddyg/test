package com.EmployeeApp.MyApp.Service;

import com.EmployeeApp.MyApp.Entity.EmployeeEntity;

import java.util.List;

public interface EmployeeService {
    List<EmployeeEntity> getAllEmployee();

    EmployeeEntity saveEmployee (EmployeeEntity employee);
    EmployeeEntity getEmployeeById(Long id);

    EmployeeEntity updateEmployee(EmployeeEntity employeeEntity);

    void deleteEmployeeById(Long id);

    public List<EmployeeEntity> getAllEmployees();
    public byte[] getEmployeeImageById(Long id);
}
