package com.example.api.model;

public class TravelDetails {
	private String countryVisited;
	private String dateVisited;

	public TravelDetails(String countryVisited, String dateVisited) {
		super();
		this.countryVisited = countryVisited;
		this.dateVisited = dateVisited;
	}

	public String getCountryVisited() {
		return countryVisited;
	}

	public void setCountryVisited(String countryVisited) {
		this.countryVisited = countryVisited;
	}

	public String getDateVisited() {
		return dateVisited;
	}

	public void setDateVisited(String dateVisited) {
		this.dateVisited = dateVisited;
	}

	@Override
	public String toString() {
		return "Travel [countryVisited=" + countryVisited + ", dateVisited=" + dateVisited + "]";
	}

}
