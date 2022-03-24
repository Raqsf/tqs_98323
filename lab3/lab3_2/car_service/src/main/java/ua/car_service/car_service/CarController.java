package ua.car_service.car_service;

import java.util.ArrayList;
import java.util.List;

/**
 * CarController
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CarController {

    // @AutoWired
    private final CarManagerService carManagerservice;

    public CarController(CarManagerService carManagerservice) {
        this.carManagerservice = carManagerservice;
    }

    @PostMapping("/car")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        /* HttpStatus status = HttpStatus.CREATED;
        Car saved =  */
        return null;
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return new ArrayList<Car>();
    }

    @GetMapping("/car")
    public ResponseEntity<Car> getCarById(long id) {
        return null;
    }
}