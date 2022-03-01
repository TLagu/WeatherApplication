package com.sda.weather.forecast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForecastController {
    // dependency inversion
    private final ForecastService forecastService;
    private final ObjectMapper objectMapper;
    private final static String API_KEY = "a7f56c4235d1e36534313e315ea5e35a";

    // GET: /forecast (endpoint API)
    public String getForecast(Long locationId, Integer period) {
        Forecast forecast = forecastService.getForecast(locationId, period);
        ForecastDTO forecastDTO = mapToForecastDTO(forecast);
        try {
            return objectMapper.writeValueAsString(forecastDTO);
        } catch (JsonProcessingException e) {
            return String.format("{\"message\":\"%s\"}", e.getMessage());
        }
    }

    private ForecastDTO mapToForecastDTO(Forecast forecast) {
        ForecastDTO responseBody = new ForecastDTO();
        responseBody.setId(forecast.getId());
        responseBody.setTemperature(forecast.getTemperature());
        responseBody.setHumidity(forecast.getHumidity());
        responseBody.setWindSpeed(forecast.getWindSpeed());
        responseBody.setWindDirection(forecast.getWindDirection().toString());
        responseBody.setPressure(forecast.getPressure());
        return responseBody;
    }
}
