package com.EmployeeApp.MyApp.Service.impl;

import com.EmployeeApp.MyApp.Entity.EmployeeEntity;
import com.EmployeeApp.MyApp.Repository.EmployeeRepository;
import com.EmployeeApp.MyApp.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;



    @Override
    public List<EmployeeEntity> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeEntity saveEmployee(EmployeeEntity employee) {

        System.out.println("Saving employee: " + employee.getFirstName());
        return employeeRepository.save(employee) ;
    }

    @Override
    public EmployeeEntity getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(new EmployeeEntity());
    }


    @Override
    public EmployeeEntity updateEmployee(EmployeeEntity employeeEntity) {


        return employeeRepository.save(employeeEntity) ;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);


//        public List<EmployeeEntity> getAllEmployees() {
//            return employeeRepository.findAll();
//        }


    }

    @Override
    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public byte[] getEmployeeImageById(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employee.getImage();
    }


}
