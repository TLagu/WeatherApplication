package com.sda.weather.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.location.Location;
import com.sda.weather.location.LocationService;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ForecastService {

    private final LocationService locationService;
    private final ForecastRepository forecastRepository;
    private Instant creationDate;
    private Instant forecastDate;
    private final static String API_KEY = "a7f56c4235d1e36534313e315ea5e35a";

    public Forecast getForecast(Long locationId, Integer period, ObjectMapper objectMapper) {
        checkPeriod(period);
        Location location = locationService.getLocation(locationId);
        creationDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
        forecastDate = Instant.now().truncatedTo(ChronoUnit.DAYS).plus(period, ChronoUnit.DAYS);
        return getFromDBOrHTTP(location, period, objectMapper);
    }

    public List<Forecast> getAllForecasts(int period, ObjectMapper objectMapper) {
        checkPeriod(period);
        List<Location> locations = locationService.getLocations();
        creationDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
        forecastDate = Instant.now().truncatedTo(ChronoUnit.DAYS).plus(period, ChronoUnit.DAYS);
        return locations.stream()
                .map(l -> getFromDBOrHTTP(l, period, objectMapper))
                .collect(Collectors.toList());
    }

    private void checkPeriod(Integer period) {
        if (period < 0 || 5 < period) {
            throw new IllegalArgumentException("Niepoprawna ilość dni.");
        }
    }

    private Forecast getFromDBOrHTTP(Location location, Integer period, ObjectMapper objectMapper) {
        Optional<Forecast> forecastOptional =
                forecastRepository.getByLocationAndCreatedDateAndForecastDate(location, creationDate, forecastDate);
        if (forecastOptional.isPresent()) {
            return forecastOptional.get();
        }
        Forecast forecast = getForecastFromHTTP(location, period, objectMapper);
        return forecastRepository.save(forecast);
    }

    private Forecast getForecastFromHTTP(Location location, Integer period, ObjectMapper objectMapper) {
        Forecast forecast;
        StringBuilder uri = new StringBuilder("https://api.openweathermap.org/data/2.5/onecall?lat=");
        uri.append(location.getLatitude());
        uri.append("&lon=");
        uri.append(location.getLongitude());
        uri.append("&appid=");
        uri.append(API_KEY);
        uri.append("&exclude=current,minutely,hourly,alerts");
        uri.append("&units=metric");
        try {
            // create a new http client
            HttpClient httpClient = HttpClient.newHttpClient();
            // prepare a http request
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(uri.toString()))
                    .build();

            // send a http request
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            // get a body from the http response
            String body = httpResponse.body();
            // deserialize JSON -> TimeDTO
            ForecastClientResponseDto forecastClientResponseDto =
                    objectMapper.readValue(body, ForecastClientResponseDto.class);
            if (forecastClientResponseDto == null) {
                throw new RuntimeException("Invalid JSON.");
            }
            ForecastClientResponseDto.ForecastDaily fcrd =
                    getForecastFromDay(forecastClientResponseDto.getDaily(), period);
            forecast = mapToForecast(fcrd, location);
            return forecast;
        } catch (Exception e) {
            throw new RuntimeException("Connection issues: " + e.getMessage());
        }
    }

    private ForecastClientResponseDto.ForecastDaily getForecastFromDay(
            List<ForecastClientResponseDto.ForecastDaily> daily, Integer period) {
        if (daily.size() < period) {
            throw new IllegalArgumentException("Wartość okresu jest spoza zakresu.");
        }
        return daily.get(period);
    }

    private WindDirection getDirectionFromDegrees(Integer windDeg) {
        if (windDeg == null) {
            return null;
        }
        if (337.5 <= windDeg && windDeg <= 360 || 0 <= windDeg && windDeg < 22.5) {
            return WindDirection.N;
        } else if (22.5 <= windDeg && windDeg < 67.5) {
            return WindDirection.NE;
        } else if (67.5 <= windDeg && windDeg < 112.5) {
            return WindDirection.E;
        } else if (112.5 <= windDeg && windDeg < 157.5) {
            return WindDirection.SE;
        } else if (157.5 <= windDeg && windDeg < 202.5) {
            return WindDirection.S;
        } else if (202.5 <= windDeg && windDeg < 247.5) {
            return WindDirection.SW;
        } else if (247.5 <= windDeg && windDeg < 292.5) {
            return WindDirection.W;
        } else if (292.5 <= windDeg && windDeg < 337.5) {
            return WindDirection.NW;
        } else {
            return null;
        }
    }

    private Forecast mapToForecast(ForecastClientResponseDto.ForecastDaily fcrd, Location location) {
        Forecast forecast = new Forecast();
        forecast.setTemperature(Objects.requireNonNull(fcrd).getTemp().getDay());
        forecast.setHumidity(Objects.requireNonNull(fcrd).getHumidity());
        Float windSpeed = Objects.requireNonNull(fcrd).getWindSpeed();
        forecast.setWindSpeed((windSpeed == null)?null:Math.round(windSpeed));
        forecast.setWindDirection(getDirectionFromDegrees(Objects.requireNonNull(fcrd).getWindDeg()));
        forecast.setPressure(Objects.requireNonNull(fcrd).getPressure());
        forecast.setLocation(location);
        forecast.setForecastDate(forecastDate);
        forecast.setCreationDate(creationDate);
        return forecast;
    }
}
