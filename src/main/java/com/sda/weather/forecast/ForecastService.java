package com.sda.weather.forecast;

import com.sda.weather.location.Location;
import com.sda.weather.location.LocationService;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RequiredArgsConstructor
public class ForecastService {

    private final LocationService locationService;
    private final ForecastRepository forecastRepository;

    public Forecast getForecast(Long locationId, Integer period) {
        if (period < 0 || 5 < period) {
            throw new IllegalArgumentException("Niepoprawna ilość dni.");
        }
        Location location = locationService.getLocation(locationId);
        Instant creationDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant forecastDate = Instant.now().truncatedTo(ChronoUnit.DAYS).plus(period, ChronoUnit.DAYS);
        Optional<Forecast> forecastOptional =
                forecastRepository.getByLocationAndCreatedDateAndForecastDate(location, creationDate, forecastDate);
        if (forecastOptional.isPresent()) {
            return forecastOptional.get();
        }
        Forecast forecast = new Forecast();
        StringBuilder uri = new StringBuilder("https://api.openweathermap.org/data/2.5/onecall?lat=");
        uri.append(location.getLatitude());
        uri.append("&lon=");
        uri.append(location.getLongitude());
        uri.append("&appid=a7f56c4235d1e3[…]5e35a&exclude=current,minutely,hourly,alerts&units=metric");
//        try {
//            HttpClient httpClient = HttpClient.newHttpClient();                                                         // create a new http client
//            HttpRequest httpRequest = HttpRequest.newBuilder()                                                          // prepare a http request
//                    .GET()
//                    .uri(URI.create(uri.toString()))
//                    .build();
//
//            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());     // send a http request
//            String body = httpResponse.body();                                                                          // get a body from the http response
//            TimeDTO timeDTO = objectMapper.readValue(body, TimeDTO.class);                                              // deserialize JSON -> TimeDTO
//            String currentDateTime = timeDTO.getCurrentDateTime();                                                      // get a field value
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
//            LocalDateTime localDateTime = LocalDateTime.parse(currentDateTime, dateTimeFormatter);
//            Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
//            entry.setCreatedDate(instant);
//        } catch (Exception e) {
//            throw new RuntimeException("Connection issues: " + e.getMessage());
//        }
            // temporary value
            //forecast.setId(10L);
            forecast.setTemperature(100F);
            forecast.setHumidity(100);
            forecast.setWindSpeed(100);
            forecast.setWindDirection(WindDirection.ES);
            forecast.setPressure(100);
            forecast.setLocation(location);
            forecast.setForecastDate(forecastDate);
            forecast.setCreationDate(creationDate);
        return forecastRepository.save(forecast);
    }

}
