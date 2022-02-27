package com.sda.weather.location;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    Location createLocation(String locality, String longitude, String latitude, String country, String region) {
        // locality
        checkStringIsEmpty(locality, "Miejscowość jest pusta.");
        locality = locality.trim();
        // longitude
        float lon = convertToFloat(longitude, "Niepoprawna wartość długości geograficznej.");
        checkRangeValue(lon, -180, 180, "Wartość długości geograficznej jest spoza zakresu <-180, 180>.");
        // latitude
        float lat = convertToFloat(latitude, "Niepoprawna wartość szerokości geograficznej.");
        checkRangeValue(lat, -90, 90, "Wartość szerokości geograficznej jest spoza zakresu <-180, 180>.");
        // country
        checkStringIsEmpty(country, "Kraj jest pusty.");
        country = country.trim();
        // region
        if (region != null) {
            region = region.trim();
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

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    private float convertToFloat(String value, String errorMessage) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void checkRangeValue(float value, float min, float max, String errorMessage) {
        if (value < min || max < value) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void checkStringIsEmpty(String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
