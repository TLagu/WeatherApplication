package com.sda.weather.forecast;

import com.sda.weather.location.Location;

import java.time.Instant;
import java.util.Optional;

public class ForecastRepositoryMock implements ForecastRepository {
    @Override
    public Forecast save(Forecast forecast) {
        forecast.setId(100L);
        return forecast;
    }

    @Override
    public Optional<Forecast> getByLocationAndCreatedDateAndForecastDate(Location location,
                                                                         Instant creationDate,
                                                                         Instant forecastDate) {
        Forecast forecast = new Forecast();
        forecast.setId(1L);
        forecast.setTemperature(23.2F);
        forecast.setHumidity(60);
        forecast.setWindSpeed(20);
        forecast.setWindDirection(WindDirection.NE);
        forecast.setPressure(1000);
        forecast.setLocation(location);
        forecast.setCreationDate(creationDate);
        forecast.setForecastDate(forecastDate);
        return Optional.of(forecast);
    }
}
