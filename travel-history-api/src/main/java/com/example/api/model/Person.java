package com.example.api.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Person {
	private String id;
	private String passportNo;
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dob;
	private String birthCountry;
	private List<TravelDetails> travelHistory;

	public Person(String id, String passportNo, String name, Date dob, String birthCountry,
			List<TravelDetails> travelHistory) {
		super();
		this.id = id;
		this.passportNo = passportNo;
		this.name = name;
		this.dob = dob;
		this.birthCountry = birthCountry;
		this.travelHistory = travelHistory;
	}

	public void setId() {
		this.id = this.passportNo + this.birthCountry;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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
		return "Person [id=" + id + ", passportNo=" + passportNo + ", name=" + name + ", dob=" + dob + ", birthCountry="
				+ birthCountry + ", travelHistory=" + travelHistory + "]";
	}

}
