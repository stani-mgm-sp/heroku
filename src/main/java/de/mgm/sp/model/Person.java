package de.mgm.sp.model;

import java.util.UUID;

public class Person {

	private final String name;
	private final int age;
	private final String country;
	private final String id;

	public Person(String name, int age, final String country) {
		super();
		this.name = name;
		this.age = age;
		this.country = country;
		this.id = UUID.randomUUID().toString();
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getCountry() {
		return country;
	}

	public String getId() {
		return id;
	}

}
