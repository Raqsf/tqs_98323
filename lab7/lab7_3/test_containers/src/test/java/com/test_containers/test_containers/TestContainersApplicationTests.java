package com.test_containers.test_containers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;


@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestContainersApplicationTests {

	private Employee emp;

	@Container
	public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest")
		.withUsername("duke")
		.withPassword("password")
		.withDatabaseName("Employees"); 

	@Autowired
	private EmployeeRepository employeeRepository;

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
	}

	@BeforeEach
	void contextLoads() {
		emp = new Employee("Pessoa de Teste", 987654321, LocalDate.now());

		employeeRepository.save(emp);
	}

	@AfterEach
	void contextUnload() {
		employeeRepository.delete(emp);
	}

	@Test
	@Order(1)
	public void checkIfEmployeeWasCreated() {
		List<Employee> employees = employeeRepository.findByName("Pessoa de Teste");
		assertEquals(1, employees.size());
	}

	@Test
	@Order(2)
	public void checkAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		assertEquals(2, employees.size());
	}

	@Test
	@Order(3)
	public void checkGetEmployeeByPhoneNumber() {
		List<Employee> employees = employeeRepository.findByPhoneNumber(987654321);
		assertEquals(emp.getId(), employees.get(0).getId());
	}

}
