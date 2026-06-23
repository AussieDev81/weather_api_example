/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.WeatherApiExample;

import com.example.frames.WeatherApiExample.WeatherFrame;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * This class calls the OpenWeatherMap API and uses the free API endpoints. 
 * To create you own API key and use the free endpoints, visit https://api.openweathermap.org to create an account, and register for a free API key.
 * Once you have your API key, create a file called <b>config.env</b> in the <b>resources</b> directory, and add your API key to it as shown below, 
 * replacing the example API key with your actual API key:<br><br>
 * <pre>
 * # OpenWeatherMap API Key
 * API_KEY=57b7c4f23361545c60d3cd68829b2c87 
 * </pre>
 * @author Nathan Snow
 */
public class ApiCall {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(WeatherFrame.class.getName());
    
    // Load environment variables from the .env file
    Dotenv dotenv = Dotenv
            .configure()
            .filename("config.env")
            .load();
    private final String API_KEY = dotenv.get("API_KEY");

    /**
     * Gets the longitude and latitude for a location based on the location name
     * @param locationName The location name
     * @return The longitude and latitude for the location in a {@link Map}. For example: {@code {"lat": 10.73, "lon":59.91}}
     */
    public Map<String, Double> getCoordinatesByLocationName(String locationName) {
        // Encode the city name to handle spaces or special characters safely
        String encodedCity = URLEncoder.encode(locationName, StandardCharsets.UTF_8);

        // Construct the OpenWeatherMap API URL
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=" + encodedCity + "&appid=" + API_KEY;

        // Handle the response
        HttpResponse<String> response = sendRequest(url);
        if(response != null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());
            
            // Throw exception if root node entry is null (city not found)
            if(rootNode.get(0) == null){
                throw new CityNotFoundException(locationName +" could not be found, please try again");
            }
            
            // Add the returned coordinates to the map
            Map<String, Double> map = new HashMap<>();
            map.put("lat", rootNode.get(0).get("lat").doubleValue());
            map.put("lon", rootNode.get(0).get("lon").doubleValue());
            return map;
        }
        return null;
    }

    /**
     * Gets the weather data for a location by a given set of coordinates
     * @param lat The latitude
     * @param lon The longitude
     * @return The current temperature
     */
    public Double getWeatherData(double lat, double lon) {
        // Construct the OpenWeatherMap API URL
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="
                + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";

        HttpResponse<String> response = sendRequest(url);

        if(response != null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            WeatherResult weatherResult = new WeatherResult(
                    rootNode.path("name").asString(),
                    rootNode.path("coord").path("lat").asDouble(),
                    rootNode.path("coord").path("lon").asDouble(),
                    rootNode.path("weather").get(0).path("description").asString(),
                    rootNode.path("main").path("temp").asDouble(),
                    rootNode.path("main").path("feels_like").asDouble(),
                    rootNode.path("main").path("temp_min").asDouble(),
                    rootNode.path("main").path("temp_max").asDouble(),
                    rootNode.path("main").path("humidity").asInt(),
                    rootNode.path("wind").path("speed").asDouble()
            );
            logger.log(Level.INFO, "WeatherResult: {0}", weatherResult);
            
            // Return the temperature value
            return weatherResult.getTemperature();
        }
        return null;
    }

    /**
     * Sends an HTTP Get request to the given URL
     * @param url The URL to call
     * @return The {@link HttpResponse}
     */
    private HttpResponse<String> sendRequest(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            // Build the Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            // Send the Request synchronously
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Return the response if ok
            if (response.statusCode() == 200) {
                return response;
            } else {
                System.err.println("Error: Received HTTP Status Code " + response.statusCode());
                System.err.println("Details: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
