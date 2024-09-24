package com.EmployeeApp.MyApp.Repository;

import com.EmployeeApp.MyApp.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {


}
