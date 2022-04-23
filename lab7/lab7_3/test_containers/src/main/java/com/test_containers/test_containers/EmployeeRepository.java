package com.test_containers.test_containers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);
    List<Employee> findByName(String name);
    List<Employee> findByPhoneNumber(int phoneNumber);
    List<Employee> findAll();
    
}