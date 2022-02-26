package com.sda.weather.location;

import lombok.Data;

@Data
public class LocationDTO {
    private Long id;
    private String locality;
    private String coords;
    private String region;
    private String country;
}
