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

	public Person(String id, String name, String passportNo, Date dob, String birthCountry) {
		this.id = id;
		this.name = name;
		this.passportNo = passportNo;
		this.dob = dob;
		this.birthCountry = birthCountry;
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

	@Override
	public String toString() {
		return "Person [id=" + id + ", passportNo=" + passportNo + ", name=" + name + ", dob=" + dob + ", birthCountry="
				+ birthCountry + "]";
	}

}
