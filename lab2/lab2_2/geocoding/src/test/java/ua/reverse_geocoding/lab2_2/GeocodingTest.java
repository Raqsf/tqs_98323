package ua.reverse_geocoding.lab2_2;

import static org.mockito.ArgumentMatchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.reverse_geocoding.Address;
import ua.reverse_geocoding.AddressResolver;
import ua.reverse_geocoding.connection.ISimpleHttpClient;

/**
 * GeocodingTest
 */
@ExtendWith(MockitoExtension.class)
public class GeocodingTest {

    @Mock
    private ISimpleHttpClient httpClient;

    @InjectMocks
    private AddressResolver addressResolver;

    @Before
    public void setUp() throws Exception {
        
        MockitoAnnotations.openMocks(this);
        
    }

    @Test
    public void goodCoordinates() throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException{
        // coordinates have to have 6 decimal points (see AddressResolver)
        // comma -> %2C
        
        String expected = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\u00A9 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\u00A9 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.6318,\"lng\":-8.658}},\"locations\":[{\"street\":\"Parque Estacionamento da Reitoria - Univerisdade de Aveiro\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Gl\u00F3ria e Vera Cruz\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"Centro\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-193\",\"geocodeQualityCode\":\"P1AAA\",\"geocodeQuality\":\"POINT\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.631803,\"lng\":-8.657881},\"displayLatLng\":{\"lat\":40.631803,\"lng\":-8.657881},\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&type=map&size=225,160&locations=40.6318025,-8.657881|marker-sm-50318A-1&scalebar=true&zoom=15&rand=-89579936\",\"roadMetadata\":null}]}]}";
        Mockito.when(httpClient.doHttpGet(contains("location=40.631800%2C-8.65800"))).thenReturn(expected);

        Optional<Address> result = addressResolver.findAddressForLocation(40.6318, -8.658);
        assertEquals(result.get(), new Address("Parque Estacionamento da Reitoria - Univerisdade de Aveiro", "Glória e Vera Cruz", "Centro", "3810-193", null));
    }

    @Test
    public void badCoordinates() throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException {
        // coordinates belong [-180, +180]

        String expected = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\u00A9 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\u00A9 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.6318,\"lng\":-200.0}},\"locations\":[]}]}";
        Mockito.when(httpClient.doHttpGet(contains("location=200.000000%2C"))).thenReturn(expected);

        Optional<Address> result = addressResolver.findAddressForLocation(200, 200);
        assertFalse(result.isPresent());
    }
    
}