package ua.car_service.car_service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {

    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    private Car car;

    @BeforeEach
    public void setUp() throws Exception {
        car = new Car("Nissan", "Skyline R32");
    }
    
    @Test
    public void whenFindCarValidId_theReturnCar() {
        // arrange a new car and insert into db
        entityManager.persistAndFlush(car); //ensure data is persisted at this point

        // test the query method of interest
        Optional<Car> found = carRepository.findById(car.getCarId());
        assertThat(found.get()).isEqualTo(car);
    }

    @Test
    public void whenFindCarInvalidId_theReturnCar() {
        Optional<Car> found = carRepository.findById(1L);
        assertThat(found).isEmpty();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car c1 = new Car("Nissan", "280Z Fairlady");
        Car c2 = new Car("Nissan", "Skyline R32");
        Car c3 = new Car("Dodge", "Trackhawk");

        entityManager.persist(c1);
        entityManager.persist(c2);
        entityManager.persist(c3);
        entityManager.persist(car);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(4).extracting(Car::getModel).containsOnly(c1.getModel(), c2.getModel(), c3.getModel(), car.getModel());
    }


}
