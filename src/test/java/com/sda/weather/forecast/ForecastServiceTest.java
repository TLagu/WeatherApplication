package com.sda.weather.forecast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.location.LocationRepository;
import com.sda.weather.location.LocationRepositoryMock;
import com.sda.weather.location.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class ForecastServiceTest {
    LocationService locationService;
    ForecastService forecastService;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LocationRepository locationRepository = new LocationRepositoryMock();
        locationService = new LocationService(locationRepository);
        ForecastRepository forecastRepository = new ForecastRepositoryMock();
        forecastService = new ForecastService(locationService, forecastRepository);
    }

    @Test
    void getForecast_getForecast() {
        // when
        int period = 1;
        Instant creationDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant forecastDate = Instant.now().truncatedTo(ChronoUnit.DAYS).plus(period, ChronoUnit.DAYS);
        Forecast forecast = forecastService.getForecast(1L, period, objectMapper);
        // then
        assertThat(forecast.getId()).isNotNull();
        assertThat(forecast.getTemperature()).isEqualTo(23.2F);
        assertThat(forecast.getHumidity()).isEqualTo(60);
        assertThat(forecast.getWindSpeed()).isEqualTo(20);
        assertThat(forecast.getWindDirection()).isEqualTo(WindDirection.NE);
        assertThat(forecast.getPressure()).isEqualTo(1000);
        assertThat(forecast.getLocation()).isEqualTo(locationService.getLocation(1));
        assertThat(forecast.getCreationDate()).isEqualTo(creationDate);
        assertThat(forecast.getForecastDate()).isEqualTo(forecastDate);
    }

    @Test
    void getForecast_whenPeriodIsBelowBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> forecastService.getForecast(1L, -1, objectMapper));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getForecast_whenPeriodIsAboveBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> forecastService.getForecast(1L, 6, objectMapper));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getAllForecasts_getAllForecasts() {
        // when
        int period = 1;
        Instant creationDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant forecastDate = Instant.now().truncatedTo(ChronoUnit.DAYS).plus(period, ChronoUnit.DAYS);
        List<Forecast> forecasts = forecastService.getAllForecasts(period, objectMapper);
        // then
        assertThat(forecasts.size()).isEqualTo(1);
        assertThat(forecasts.get(0).getId()).isNotNull();
        assertThat(forecasts.get(0).getTemperature()).isEqualTo(23.2F);
        assertThat(forecasts.get(0).getHumidity()).isEqualTo(60);
        assertThat(forecasts.get(0).getWindSpeed()).isEqualTo(20);
        assertThat(forecasts.get(0).getWindDirection()).isEqualTo(WindDirection.NE);
        assertThat(forecasts.get(0).getPressure()).isEqualTo(1000);
        assertThat(forecasts.get(0).getLocation()).isEqualTo(locationService.getLocation(1));
        assertThat(forecasts.get(0).getCreationDate()).isEqualTo(creationDate);
        assertThat(forecasts.get(0).getForecastDate()).isEqualTo(forecastDate);
    }
}
