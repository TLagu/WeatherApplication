package com.sda.weather.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LocationController {
    // dependency inversion
    private final LocationService locationService;
    private final ObjectMapper objectMapper;

    // POST: /location (endpoint API)
    public String createLocation(String data) {
        try {
            // deserialization      JSON -> LocationDTO
            LocationDTO requestBody = objectMapper.readValue(data, LocationDTO.class);
            // service layer        String, String -> Location
            Location savedLocation = locationService.createLocation(
                    requestBody.getLocality(),
                    requestBody.getLongitude(),
                    requestBody.getLatitude(),
                    requestBody.getRegion(),
                    requestBody.getCountry()
            );
            // mapper               Location -> LocationDTO
            LocationDTO responseBody = mapToLocationDTO(savedLocation);
            // serialization        LocationDTO -> JSON
            return objectMapper.writeValueAsString(responseBody);
        } catch (Exception e) {
            // exception handling   Exception -> JSON
            return String.format("{\"message\":\"%s\"}", e.getMessage());
        }
    }

    // GET: /location (endpoint API)
    public String getLocations() {
        try {
            List<Location> entries = locationService.getLocations();
            List<LocationDTO> mappedEntries = entries.stream()
                    .map(this::mapToLocationDTO)
                    .collect(Collectors.toList());

            return objectMapper.writeValueAsString(mappedEntries);
        } catch (Exception e) {
            return String.format("{\"message\":\"%s\"}", e.getMessage());                                       // exception handling   Exception -> JSON
        }
    }

    // mapper
    private LocationDTO mapToLocationDTO(Location savedLocation) {
        LocationDTO responseBody = new LocationDTO();
        responseBody.setId(savedLocation.getId());
        responseBody.setLocality(savedLocation.getLocality());
        responseBody.setRegion(savedLocation.getRegion());
        responseBody.setCountry(savedLocation.getCountry());
        responseBody.setLongitude(savedLocation.getLongitude().toString());
        responseBody.setLatitude(savedLocation.getLatitude().toString());
        return responseBody;
    }
}
