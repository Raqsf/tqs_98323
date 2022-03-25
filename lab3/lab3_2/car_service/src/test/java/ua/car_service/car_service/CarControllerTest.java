package ua.car_service.car_service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void whenPostCar_thenCreateCar() throws IOException, Exception {
        Car car = new Car("Nissan", "180Sx");
        
        when(service.save(Mockito.any())).thenReturn(car);

        mvc.perform(
            post("/api/car").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.maker", is("Nissan")))
            .andExpect(jsonPath("$.model", is("180Sx")));
        

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws IOException, Exception {
        Car c1 = new Car("Nissan", "280Z Fairlady");
        Car c2 = new Car("Nissan", "Skyline R32");
        Car c3 = new Car("Dodge", "Trackhawk");

        List<Car> allCars = Arrays.asList(c1, c2, c3);

        when(service.getAllCars()).thenReturn(allCars);

        mvc.perform(
            get("/api/cars").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].model", is(c1.getModel())))
            .andExpect(jsonPath("$[1].model", is(c2.getModel())))
            .andExpect(jsonPath("$[2].model", is(c3.getModel())));

        verify(service, times(1)).getAllCars();
    }

    @Test
    public void whenGetACar_thenReturnJson() throws Exception {
        Car c1 = new Car("Ford", "Mustang");
        // Dodge  
            // LCAT charger
        // Ford
            // raptor

        Long id = 1L;

        when(service.getCarDetails(id)).thenReturn(Optional.of(c1));
        
        mvc.perform(
            get("/api/{id}", id).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.model", is(c1.getModel())))
            .andExpect(jsonPath("$.maker", is(c1.getMaker())));

        verify(service, times(1)).getCarDetails(id);
    }

}