package com.ford.vehicle.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.vehicle.model.Vehicle;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleJdbcRepositoryTest {


    private static final ObjectMapper om = new ObjectMapper();

//    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

//    @Autowired
    private VehicleJdbcRepository mockRepository = new VehicleJdbcRepository();

    @Test
     public  void test_findById_Ok() throws JSONException {

        String expected = "{id:b,name:\"BMW\",vin:\"V0001\"}";

            ResponseEntity<Vehicle> response = restTemplate.getForEntity("http://localhost:8080/vehicles/6", Vehicle.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("BMW", response.getBody().getName());
    }

        @Test
        public void find_allBook_OK() throws Exception {

            List<Vehicle> vehicles = Arrays.asList( new Vehicle(10001L, "BMW", "V0001"),
                    new Vehicle(10002L, "FORD", "V0006"));

            String expected = om.writeValueAsString(vehicles);

//            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/vehicles"", String.class);

            ResponseEntity<List<Vehicle>> response =
                    restTemplate.exchange("http://localhost:8080/vehicles", HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<Vehicle>>() {});


            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expected, response.getBody());

        }

    @Test
    void deleteById() {
    }

    @Test
    public void save_Vehicle_OK() throws Exception {

        Vehicle newVehicle = new Vehicle(6L, "BMW", "V0001");
//        when(mockRepository.insert(newVehicle)).thenReturn(3);

        String expected = om.writeValueAsString(newVehicle);

        ResponseEntity<String> response = (restTemplate.postForEntity("http://localhost:8080/vehicles", newVehicle, String.class));
        System.out.println(response.getStatusCode());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    void update() {
    }

//    @Test
//    void deleteByVin() {
//
//        Vehicle vehicle = mockRepository.findById(5);
//        ResponseEntity<String> response=
//
//    }
}