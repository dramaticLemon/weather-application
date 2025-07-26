package com.dch.compilers.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {
	private String uuid;
	private String cityName;
	private double temperature;
	private double  feelsLike;
	private String desctiption;
	private String icon;
	private int humidity;
	private double windSpeed;

	public WeatherDto() {}
	
	public WeatherDto(String cityName, double temperature, double feelsLike, String desctiption, String icon,
			int humidity, double windSpeed) {
		this.uuid = UUID.randomUUID().toString();
		this.cityName = cityName;
		this.temperature = temperature;
		this.feelsLike = feelsLike;
		this.desctiption = desctiption;
		this.icon = icon;
		this.humidity = humidity;
		this.windSpeed = windSpeed;
	}

	// public void convertTemperaturesToCelsius() {
    //     this.temperature = this.temperature - 273.15;
    //     this.feelsLike = this.feelsLike - 273.15;
    // }
	
	public String getUuid() {
		return this.uuid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getFellsLike() {
		return feelsLike;
	}

	public void setFellsLike(double feelsLike) {
		this.feelsLike = feelsLike;
	}

	public String getDesctiption() {
		return desctiption;
	}

	public void setDesctiption(String desctiption) {
		this.desctiption = desctiption;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	@Override
	public String toString() {
		return "WeatherDto [uuid=" + uuid + ", cityName=" + cityName + ", temperature=" + temperature + ", feelsLike="
				+ feelsLike + ", desctiption=" + desctiption + ", icon=" + icon + ", himidity=" + humidity
				+ ", windSpeed=" + windSpeed + "]";
	}

	
	
	
}
