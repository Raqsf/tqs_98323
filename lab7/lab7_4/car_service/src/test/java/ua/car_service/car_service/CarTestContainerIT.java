package ua.car_service.car_service;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import ua.car_service.car_service.Car;
import ua.car_service.car_service.CarRepository;
import ua.car_service.car_service.CarServiceApplication;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CarServiceApplication.class)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
@AutoConfigureMockMvc
public class CarTestContainerIT {
    
    @Container
	public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest")
		.withUsername("duke")
		.withPassword("password")
		.withDatabaseName("Employees"); 

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepository repository;

    private Car car1, car2, car3, car4;

    @BeforeEach
	void contextLoads() {
        RestAssuredMockMvc.mockMvc(mvc);
        car1 = new Car("Nissan", "180Sx");
        car2 = new Car("Nissan", "Skyline R32");
        car3 = new Car("Dodge", "Trackhawk");
        car4 = new Car("Ford", "Raptor");

        repository.save(car2);
        repository.save(car3);
        repository.save(car4);
	}

    @Test
    void whenValidInput_thenCreateCar() {
        RestAssuredMockMvc.given().contentType("application/json")
            .body(car1)
            .when()
                .request("POST", "/api/car")
            .then()
                .statusCode(201)
                .body("maker", equalTo("Nissan"))
                .body("model", equalTo("180Sx"));
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        RestAssuredMockMvc.given().contentType("application/json")
            .when()
                .request("GET", "/api/cars")
            .then()
                .statusCode(200)
                .body("model", hasItems(car2.getModel(), car3.getModel()));
    }

    @Test
    void whenGetAValidCarId_thenStatus200() {

        RestAssuredMockMvc.given().contentType("application/json")
            .when()
                .request("GET", "/api/details/" + car4.getCarId())
            .then()
                .statusCode(200)
                .body("model", equalTo(car4.getModel()));
    }
}
