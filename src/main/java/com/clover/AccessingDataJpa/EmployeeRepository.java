package com.clover.AccessingDataJpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clover.Employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
