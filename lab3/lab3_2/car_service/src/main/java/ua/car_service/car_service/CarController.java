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
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarManagerService carManagerservice;

    @PostMapping("/car")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        System.out.println("POST");
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carManagerservice.save(car);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        System.out.println("GET ALL");
        return carManagerservice.getAllCars();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value="id") long id) {
        System.out.println("GET ONE");
        System.out.println(id);
        Optional<Car> car = carManagerservice.getCarDetails(id);

        if(car.isPresent()) {
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        } 
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        
        /* System.out.println(carManagerservice.getCarDetails(id).get());
        return ResponseEntity.ok().body(carManagerservice.getCarDetails(id).get()); */
    }
}