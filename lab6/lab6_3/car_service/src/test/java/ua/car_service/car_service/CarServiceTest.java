package ua.car_service.car_service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carService;

    @BeforeEach
    public void setUp() {
        //these expectations provide an alternative to the use of the repository

        Car c1 = new Car("Dodge", "Hellcat Charger");
        Car c2 = new Car("Ford", "Raptor");
        Car c3 = new Car("Nissan", "180Sx");

        c1.setCarId(1L);

        List<Car> allCars = Arrays.asList(c1, c2, c3);

        when(carRepository.findById(c1.getCarId())).thenReturn(Optional.of(c1));
        when(carRepository.findById(-12L)).thenReturn(Optional.empty());        
        when(carRepository.findAll()).thenReturn(allCars);

    }

    // service get all, get by id, get by model, get by maker

    @Test
    public void whenSearchValidId_thenCarShouldBeFound() {
        Optional<Car> fromDB = carService.getCarDetails(1L);
        assertThat(fromDB.get().getModel()).isEqualTo("Hellcat Charger");
        assertThat(fromDB.get().getMaker()).isEqualTo("Dodge");

        verify(carRepository, times(1)).findById(anyLong());
    }

    @Test
    public void whenSearchInvalidId_thenCarShouldNotBeFound() {
        Optional<Car> fromDB = carService.getCarDetails(-12L);

        verify(carRepository, times(1)).findById(anyLong());

        assertThat(fromDB).isEmpty();
    }

    @Test
    public void given3Cars_whengetAll_thenReturn3Records() {
        Car c1 = new Car("Dodge", "Hellcat Charger");
        Car c2 = new Car("Ford", "Raptor");
        Car c3 = new Car("Nissan", "180Sx");

        List<Car> allCars = carService.getAllCars();

        verify(carRepository, times(1)).findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(c1.getModel(), c2.getModel(), c3.getModel());
    }

    @Test
    public void whenSaveCar_thenReturnCar() {
        Car c1 = new Car("Ford", "Mustang");

        when(carRepository.save(c1)).thenReturn(c1);

        Car saved = carService.save(c1);

        verify(carRepository, times(1)).save(c1);
        assertThat(saved).isEqualTo(c1);
    }
}
