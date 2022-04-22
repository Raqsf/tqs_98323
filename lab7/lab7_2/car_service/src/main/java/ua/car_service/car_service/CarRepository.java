package ua.car_service.car_service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CarRepository
 */

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    public List<Car> findAll();
    public Car findByCarId(Long id);
    
}