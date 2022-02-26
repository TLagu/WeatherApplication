package com.sda.weather.location;

import lombok.Data;

@Data
public class LocationDTO {
    private Long id;
    private String locality; // todo what is it? mb this is a city
    private String coords; // todo how to pass longitude and latitude?
    private String region;
    private String country;
}
