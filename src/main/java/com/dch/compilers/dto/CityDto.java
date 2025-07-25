package com.dch.compilers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CityDto {
	String name;

	@JsonProperty("lat")
	Double latitude;

	@JsonProperty("lon")
	Double longitude;

	String country;

	String state;

	public CityDto() {

	}
	
	public CityDto(String name, Double latitude, String country, String state) {
		this.name = name;
		this.latitude = latitude;
		this.country = country;
		this.state = state;
	}

	public String getName() {
		return name;	
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "CityDto [name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + ", country=" + country
				+ ", state=" + state + "]";
	}
	
	
}
