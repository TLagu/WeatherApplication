package com.sda.weather.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocationController {
    // dependency inversion
    private final LocationService locationService;
    private final ObjectMapper objectMapper;

    // POST: /location (endpoint API)
    public String createLocation(String data) {
        try {
            // deserialization      JSON -> EntryDTO
            LocationDTO requestBody = objectMapper.readValue(data, LocationDTO.class);
            // service layer        String, String -> Entry
            Location savedLocation = locationService.createLocation(
                    requestBody.getLocality(),
                    requestBody.getLongitude(),
                    requestBody.getLatitude(),
                    requestBody.getRegion(),
                    requestBody.getCountry()
            );
            // mapper               Entry -> EntryDTO
            LocationDTO responseBody = mapToLocationDTO(savedLocation);
            // serialization        EntryDTO -> JSON
            return objectMapper.writeValueAsString(responseBody);
        } catch (Exception e) {
            // exception handling   Exception -> JSON
            return String.format("{\"message\":\"%s\"}", e.getMessage());
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
