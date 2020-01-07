//package com.ford.vehicle.repository;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ford.vehicle.model.Vehicle;
//import org.json.JSONException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//
//class VehicleJdbcRepositoryTest {
//
//    private static final ObjectMapper om = new ObjectMapper();
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private VehicleJdbcRepository mockRepository;
//
////    @Test
////    public void find_vehicleVin_OK() throws JSONException {
////
////        Vehicle vehicle = new Vehicle(1L, "BMW", "V0002");
////        String expected = "{id:1,name:\"BMW\",vin:\"V0002\"}";
////
//////        when(mockRepository.findById(1L)).thenReturn(Optional.of( vehicle));
////
////        when(mockRepository.findById(1L)).thenReturn(vehicle);
////
//////        ResponseEntity<String> response = restTemplate.getForEntity("/vehicle/v0002", String.class);
////
////
////        restTemplate.ex
////        ResponseEntity<Vehicle> response = restTemplate.exchange("/vehicle/v0002", Vehicle.class);
////        assertEquals(HttpStatus.OK, response.getStatusCode());
//////        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
////
////        JSONAssert.assertEquals(expected, response.getBody(), false);
////
////
////    }
//
//}