package com.ford.vehicle.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public  class VehicleRowMapper implements RowMapper<Vehicle> {
	@Override
	public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
		Vehicle vehicle = new Vehicle();
		vehicle.setVid(rs.getLong("vid"));
		vehicle.setName(rs.getString("name"));
		vehicle.setVin(rs.getString("vin"));
		return vehicle;
	}



}
