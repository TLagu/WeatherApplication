package com.sda.weather.location;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    Location createLocation(String locality, String longitude, String latitude, String country, String region) {
        if (locality == null || locality.isBlank()) {
            throw new IllegalArgumentException("Miejscowość jest pusta.");
        }
        float lon;
        try {
            lon = Float.parseFloat(longitude);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Niepoprawna wartość długości geograficznej.");
        }
        float lat;
        try {
            lat = Float.parseFloat(latitude);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Niepoprawna wartość szerokości geograficznej.");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Kraj jest pusty.");
        }

        Location location = new Location();
        location.setLocality(locality);
        location.setLongitude(lon);
        location.setLatitude(lat);
        location.setCountry(country);
        location.setRegion(region);

        // data access layer
        return locationRepository.save(location);
    }
}
