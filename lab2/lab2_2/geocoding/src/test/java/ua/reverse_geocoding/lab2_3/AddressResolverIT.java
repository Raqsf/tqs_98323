package ua.reverse_geocoding.lab2_3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import ua.reverse_geocoding.Address;
import ua.reverse_geocoding.AddressResolver;
import ua.reverse_geocoding.connection.ISimpleHttpClient;
import ua.reverse_geocoding.connection.TqsBasicHttpClient;

public class AddressResolverIT {

    private ISimpleHttpClient httpClient;

    private AddressResolver addressResolver;

    @Before
    public void setUp() throws Exception {
        
        httpClient = new TqsBasicHttpClient();
        addressResolver = new AddressResolver(httpClient);
        
    }

    @Test
    public void goodCoordinatesTest() throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException{
        
        Optional<Address> result = addressResolver.findAddressForLocation(40.6318, -8.658);
        assertEquals(result.get(), new Address("Parque Estacionamento da Reitoria - Univerisdade de Aveiro", "Glória e Vera Cruz", "Centro", "3810-193", null));
    }

    @Test
    public void badCoordinatesTest() throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException {
        // coordinates belong [-180, +180]

        Optional<Address> result = addressResolver.findAddressForLocation(200, 200);
        assertFalse(result.isPresent());
    }
    
}
