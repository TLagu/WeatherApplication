package com.sda.weather.location;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    Location createLocation(String locality, String longitude, String latitude, String country, String region) {
        if (locality == null || locality.isBlank()) {
            throw new IllegalArgumentException("Miejscowość jest pusta.");
        }
        var lon = parseToFloat(longitude, "Niepoprawna wartość długości geograficznej.");
        var lat = parseToFloat(latitude, "Niepoprawna wartość szerokości geograficznej.");
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Kraj jest pusty.");
        }

        var location = new Location();
        location.setLocality(locality);
        location.setLongitude(lon);
        location.setLatitude(lat);
        location.setCountry(country);
        location.setRegion(region);

        // data access layer
        return locationRepository.save(location);
    }

    private float parseToFloat(String value, String exceptionMessage) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    void dummyMethod() {
        System.out.println(1);
    }
}
