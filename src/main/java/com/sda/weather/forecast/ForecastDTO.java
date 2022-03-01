package com.sda.weather.forecast;

import lombok.Data;

@Data
public class ForecastDTO {
    private Long id;
    private Float temperature;
    private Integer humidity;
    private Integer windSpeed;
    private String windDirection;
    private Integer pressure;
}
