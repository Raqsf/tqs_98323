package ua.car_service.car_service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// Ex 2
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CarServiceApplication.class)

// Ex 2 Test E
// @AutoConfigureTestDatabase

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database

// Ex 3
@TestPropertySource( locations = "application-integrationtest.properties")
public class CarControllerIT {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car car = new Car("Nissan", "Skyline R32");
        restTemplate.postForEntity("/api/car", car, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly(car.getModel());
    }

    @Test
    void givenCars_whenGetCars_thenStatus200()  {
        Car c1 = new Car("Nissan", "Skyline R32");
        Car c2 = new Car("Dodge", "Trackhawk");
        repository.save(c1);
        repository.save(c2);
        /* Ex2
        createTestCar("Nissan", "Skyline R32");
        createTestCar("Dodge", "Trackhawk"); */


        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("Skyline R32", "Trackhawk");

    }

    @Test
    void whenGetAValidCarId_thenStatus200() {
        Car car = new Car("Ford", "Raptor");
        repository.save(car);
        // Ex 2
        // repository.saveAndFlush(car);

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/details/" + String.valueOf(car.getCarId()), Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).isEqualTo("Raptor");
    }

    // Ex 2
    /* 
    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        repository.saveAndFlush(car);
    }
     */
}
