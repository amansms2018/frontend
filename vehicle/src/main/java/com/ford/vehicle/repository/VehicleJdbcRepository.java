package com.ford.vehicle.repository;

import com.ford.vehicle.model.VehicleRowMapper;
import com.ford.vehicle.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleJdbcRepository{
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Vehicle findById(long id) {
		return jdbcTemplate.queryForObject("select * from Vehicle where vid=?", new Object[] { id },
				new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
	}

	public List<Vehicle> findAll() {
		return jdbcTemplate.query("select * from Vehicle", new VehicleRowMapper());
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from student where id=?", new Object[] { id });
	}

	public int insert(Vehicle vehicle) {
		return jdbcTemplate.update("insert into Vehicle (vid, name, vin) " + "values(?,  ?, ?)",
				new Object[] { vehicle.getVid(), vehicle.getName(), vehicle.getVin()});
	}

	public int update(Vehicle vehicle) {
		return jdbcTemplate.update("update Vehicle " + " set name = ?, vin = ? " + " where vid = ?",
				new Object[] {vehicle.getName(), vehicle.getVin(), vehicle.getVid()});
	}

	public int deleteByVin(String vin) {
		return jdbcTemplate.update("delete from Vehicle where vin=?", new Object[] { vin });
	}


}

