package ua.car_service.car_service;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import ua.car_service.car_service.Car;
import ua.car_service.car_service.CarController;
import ua.car_service.car_service.CarManagerService;

@WebMvcTest(CarController.class)
public class CarAssuredControllerTest {

    private Car car;

    @Autowired
    private MockMvc mvc;

    @MockBean CarManagerService service;

    @BeforeEach
    public void setUp() {
        car = new Car("Nissan", "180Sx");
    }

    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);
        
        when(service.save(Mockito.any())).thenReturn(car);

        RestAssuredMockMvc.given().contentType("application/json")
            .body(car)
            .when()
                .request("POST", "/api/car")
            .then()
                .statusCode(201)
                .body("maker", equalTo("Nissan"))
                .body("model", equalTo("180Sx"));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car c1 = new Car("Nissan", "280Z Fairlady");
        Car c2 = new Car("Nissan", "Skyline R32");
        Car c3 = new Car("Dodge", "Trackhawk");

        List<Car> allCars = Arrays.asList(c1, c2, c3);

        when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc.given().contentType("application/json")
            .when()
                .get("/api/cars")
            .then()
                .statusCode(200)
                .body("$.size()", equalTo(3))
                .body("[0].model", equalTo(c1.getModel()))
                .body("[1].model", equalTo(c2.getModel()))
                .body("[2].model", equalTo(c3.getModel()));

        verify(service, times(1)).getAllCars();
    }

    @Test
    public void whenGetACar_thenReturnJson() throws Exception {
        Long id = 1L;

        when(service.getCarDetails(id)).thenReturn(Optional.of(car));
        
        RestAssuredMockMvc.given().contentType("application/json")
            .when()
                .get("/api/details/" + id)
            .then()
                .statusCode(200)
                .body("model", equalTo(car.getModel()))
                .body("maker", equalTo(car.getMaker()));
                
        verify(service, times(1)).getCarDetails(id);
    }
    
}
