package de.mgm.sp.model;

import java.util.UUID;

public class Car {

	private final String brand;
	private final String model;
	private final String country;
	private final String id;

	public Car(String brand, String model, final String country) {
		super();
		this.brand = brand;
		this.model = model;
		this.country = country;
		this.id = UUID.randomUUID().toString();
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public String getCountry() {
		return country;
	}

	public String getId() {
		return id;
	}
}
