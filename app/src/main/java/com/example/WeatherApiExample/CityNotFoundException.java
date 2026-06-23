/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.WeatherApiExample;

/**
 *
 * @author Nathan Snow
 */
public class CityNotFoundException extends RuntimeException {
    
    public CityNotFoundException(){
        super();
    }
    
    public CityNotFoundException(String msg){
        super(msg);
    }
}
