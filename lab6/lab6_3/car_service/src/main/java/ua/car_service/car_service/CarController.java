package ua.car_service.car_service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * CarController
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarManagerService carManagerservice;

    @PostMapping("/car")
    public ResponseEntity<Car> createCar(@RequestBody CarDTO car) {
        HttpStatus status = HttpStatus.CREATED;
        Car persistentCar = new Car(car.getMaker(), car.getModel());
        Car saved = carManagerservice.save(persistentCar);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carManagerservice.getAllCars();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value="id") long id) {
        Optional<Car> car = carManagerservice.getCarDetails(id);

        return car.isPresent() ? ResponseEntity.ok().body(car.get()) : ResponseEntity.of(car);
        
    }
}