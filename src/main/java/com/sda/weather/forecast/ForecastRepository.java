package com.sda.weather.forecast;

import com.sda.weather.location.Location;

import java.time.Instant;
import java.util.Optional;

public interface ForecastRepository {

    Optional<Forecast> getByLocationAndCreatedDateAndForecastDate(Location location,
                                                                  Instant creationDate,
                                                                  Instant forecastDate);

    Forecast save(Forecast forecast);

}
