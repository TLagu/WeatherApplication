package com.sda.weather.location;

import lombok.Data;

@Data
public class LocationDTO {
    private Long id;
    private String locality;
    private String longitude;
    private String latitude;
    private String region;
    private String country;
}
