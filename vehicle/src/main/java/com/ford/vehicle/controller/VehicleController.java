package com.ford.vehicle.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import com.ford.vehicle.exceptions.ResourceNotFoundException;
import com.ford.vehicle.model.Vehicle;
import com.ford.vehicle.repository.VehicleJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1")
public class VehicleController {

  @Autowired
  private VehicleJdbcRepository vehicleJdbcRepository;


  @GetMapping("/vehicles")   // GET Method for reading operation
  public List<Vehicle> getAllVehicles() {
    return vehicleJdbcRepository.findAll();
  }


  @GetMapping("/vehicles/{vid}")    // GET Method for Read operation
  public ResponseEntity<?> getCarsById(@PathVariable ("vid") Long vid)  throws ResourceNotFoundException {

    Vehicle vehicle = vehicleJdbcRepository.findById(vid);

    if(vehicle== null) {
      return  ResponseEntity.badRequest().body(new ResourceNotFoundException("Vehicle " + vid + " not found"));
    }

    else
      return ResponseEntity.ok().body(vehicle);
  }




//  @PostMapping("/vehicles")
//  public ResponseEntity<?> createCar(@Valid @RequestBody Vehicle vehicle) {
//    vehicleJdbcRepository.insert(vehicle);
//    return new ResponseEntity<String>(vehicleJdbcRepository.insert(vehicle),HttpStatus.CREATED);
//  }

  @PostMapping("/vehicles")
  public ResponseEntity<String> reateCar(@RequestBody Vehicle vehicle) {
    return new ResponseEntity<>(String.valueOf(vehicleJdbcRepository.insert(vehicle)),HttpStatus.CREATED) ;
  }







  @PutMapping("/vehicles/{vid}")    // PUT Method for Update operation
  public ResponseEntity<Vehicle> updateVehicle(
          @PathVariable(value = "id") Long vid, @Valid @RequestBody Vehicle VehicleDetails)
          throws ResourceNotFoundException {

    Vehicle vehicle = vehicleJdbcRepository.findById(vid);
    int count= vehicleJdbcRepository.update(VehicleDetails);
    Vehicle updatedCar = VehicleDetails;
    return ResponseEntity.ok(updatedCar);
  }


  @DeleteMapping("/vehicles/{vid}")    // DELETE Method for Delete operation
  public ResponseEntity<?> deleteCar(@PathVariable(value = "vid") Long vid) throws Exception {
    Vehicle vehicle = vehicleJdbcRepository
            .findById(vid);

    if(vehicle== null) {
      vehicleJdbcRepository.deleteByVin(vehicle.getVin());
      return  ResponseEntity.badRequest().body(new ResourceNotFoundException("Vehicle " + vid + " not found"));
    }

    else
      return ResponseEntity.ok().body("vehicle with" + vid  + " is deleted ");
  }


  // update Vehicle name only
  @PatchMapping("/vehicles/{id}")
  Vehicle patch(@RequestBody Map<String, String> update, @PathVariable Long sid) throws  Exception {

    Vehicle vehicle = vehicleJdbcRepository.findById(sid);

    String  vName= update.get("name");
    if (!StringUtils.isEmpty(vName)) {
      vehicle.setName(update.get("name"));
      vehicleJdbcRepository.update(vehicle);
    }
    else {
      throw new ResourceNotFoundException(update.keySet().toString());
    }

    return  vehicle;

  }
}
