package ua.car_service.car_service;

import java.util.List;
import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;

/**
 * CarManagerService
 */
public class CarManagerService {

    // @Autowired
    private CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car carEntity) {
        return carRepository.save(carEntity);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long id) {
        return carRepository.findById(id);
    }

    
}