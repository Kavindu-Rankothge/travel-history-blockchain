package com.example.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Person")
public class Person {
	@Id
	private String id;
	private String name;
	private int age;
	private String birthCountry;
	private List<TravelDetails> travelHistory;

	public Person(String id, String name, int age, String birthCountry, List<TravelDetails> travelHistory) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.birthCountry = birthCountry;
		this.travelHistory = travelHistory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	public List<TravelDetails> getTravelHistory() {
		return travelHistory;
	}

	public void setTravelHistory(List<TravelDetails> travelHistory) {
		this.travelHistory = travelHistory;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + ", birthCountry=" + birthCountry
				+ ", travelHistory=" + travelHistory + "]";
	}	

}
