/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.WeatherApiExample;

/**
 * This class defines whatever details we want it to capture from the OpenWeatherMap API response.
 * Here we are collecting much more than just the current temperature, so you could add much more 
 * info to the GUI if you wanted to.
 * @author Nathan Snow
 */
public class WeatherResult {
    private String cityName;
    private double latitude;
    private double longitude;
    private String weatherDescription;
    private double temperature;
    private double feelsLikeTemperature;
    private double minTemperature;
    private double maxTemperature;
    private int humidity;
    private double windSpeed;

    public WeatherResult(String cityName, double latitude, double longitude, String weatherDescription,
                         double temperature, double feelsLikeTemperature,
                         double minTemperature, double maxTemperature, int humidity, double windSpeed) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    // Getters
    public String getCityName() {
        return cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    // Setters
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setFeelsLikeTemperature(double feelsLikeTemperature) {
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return """
                City: %s
                Latitude: %.2f
                Longitude: %.2f
                Weather: %s
                Temperature: %.2f °C
                Feels Like: %.2f °C
                Min Temperature: %.2f °C
                Max Temperature: %.2f °C
                Humidity: %d%%
                Wind Speed: %.2f m/s
                """.formatted(cityName, latitude, longitude, weatherDescription, temperature, feelsLikeTemperature,
                minTemperature, maxTemperature, humidity, windSpeed);
    }
}
