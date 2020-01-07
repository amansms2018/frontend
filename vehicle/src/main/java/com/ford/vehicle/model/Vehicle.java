package com.ford.vehicle.model;

public class Vehicle {
	private Long vid;
	private String name;
	private String vin;

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public Long getVid() {
		return vid;
	}

	public String getName() {
		return name;
	}


	public String getVin() {
		return vin;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Vehicle() {
	}

	public Vehicle(Long vid, String name, String vin) {
		this.vid = vid;
		this.name = name;
		this.vin = vin;
	}

	@Override
	public String toString() {
		return "{" +
				"vid=" + vid +
				", name='" + name + '\'' +
				", vin='" + vin + '\'' +
				'}';
	}
}