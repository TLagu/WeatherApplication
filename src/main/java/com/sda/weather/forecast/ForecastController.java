package com.sda.weather.forecast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ForecastController {
    // dependency inversion
    private final ForecastService forecastService;
    private final ObjectMapper objectMapper;

    // GET: /forecast (endpoint API)
    public String getForecast(Long locationId, Integer period) {
        Forecast forecast = forecastService.getForecast(locationId, period, objectMapper);
        ForecastDTO forecastDTO = mapToForecastDTO(forecast);
        try {
            return objectMapper.writeValueAsString(forecastDTO);
        } catch (JsonProcessingException e) {
            return String.format("{\"message\":\"%s\"}", e.getMessage());
        }
    }

    public String getAllForecasts(int period) {
        List<Forecast> forecasts = forecastService.getAllForecasts(period, objectMapper);
        List<ForecastDTO> forecastsDTO = mapToForecastsDTO(forecasts);
        try {
            return objectMapper.writeValueAsString(forecastsDTO);
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

    private List<ForecastDTO> mapToForecastsDTO(List<Forecast> forecast) {
        return forecast.stream()
                .map(this::mapToForecastDTO)
                .collect(Collectors.toList());
    }
}
