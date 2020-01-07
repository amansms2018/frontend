package com.ford.vehicle;

import com.ford.vehicle.model.Vehicle;
import com.ford.vehicle.repository.VehicleJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VehicleManagementSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VehicleManagementSystemApplication.class, args);
	}


	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	VehicleJdbcRepository  repository;

	@Override
	public void run(String... args) throws Exception {

//		logger.info("Vehicle id 10001 -> {}", repository.findById(10001L));
//
//
////		System.out.println("\n\n Student id 10001 ->" + repository.findById(10001L));
//
//
//		logger.info("Inserting -> {}", repository.insert(new Vehicle(10010L, "TOYOTA", "V0005")));
//
//		logger.info("All Vehicle 1 -> {}", repository.findAll());
////
//		logger.info("Update 10001 -> {}", repository.update(new Vehicle(10001L, "Updated", "V0006")));
//
//
//
////		repository.deleteById(10002L);
////
////		logger.info("All Vehicle 2 -> {}", repository.findAll());




	}
}
