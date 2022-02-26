package com.sda.weather.location;

import java.util.Collections;
import java.util.List;

public class LocationRepositoryMock implements LocationRepository {
    @Override
    public Location save(Location location) {
        location.setId(100L);
        return location;
    }

    @Override
    public List<Location> findAll() {
        Location location = new Location();
        location.setId(1L);
        location.setLocality("Szczecin");
        location.setLongitude(14.54F);
        location.setLatitude(53.45F);
        location.setCountry("Polska");
        location.setRegion("Zachodniopomorskie");
        return Collections.singletonList(location);
    }
}
