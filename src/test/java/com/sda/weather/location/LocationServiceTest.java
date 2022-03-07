package com.sda.weather.location;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class LocationServiceTest {
    LocationService locationService;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LocationRepository locationRepository = new LocationRepositoryMock();
        locationService = new LocationService(locationRepository);
    }

    @Test
    void createLocation_createsLocation() {
        // when
        Location location = locationService.createLocation(
                "Szczecin", "53.45", "14.54", "Poland", "Zachodniopomorskie"
        );
        // then
        assertThat(location.getId()).isNotNull();
        assertThat(location.getLocality()).isEqualTo("Szczecin");
        assertThat(location.getLongitude()).isEqualTo("53.45");
        assertThat(location.getLatitude()).isEqualTo("14.54");
        assertThat(location.getCountry()).isEqualTo("Poland");
        assertThat(location.getRegion()).isEqualTo("Zachodniopomorskie");
    }

    @Test
    void createLocation_whenLocalityIsBlank_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "   ", "53.45", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLocalityIsNull_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                null, "53.45", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLongitudeIsNotNumber_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "number", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLongitudeIsBelowBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "-180.1", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLongitudeIsAboveBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "180.1", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLongitudeIsOnLowerBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "-180.0", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLongitudeIsOnUpperBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "180.0", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLongitudeIsInsideBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "54.45", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIsNotNumber_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "53.45", "number", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIsBelowBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "53.45", "-90.0", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIsAboveBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "53.45", "90.0", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIsOnLowerBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "53.45", "-90.0", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIsOnUpperBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "53.45", "90.0", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIsInsideBoundary_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "Szczecin", "53.45", "14.54", "Poland", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenCountryIsBlank_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                "   ", "53.45", "14.54", "    ", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenCountryIsNull_throwsAnException() {
        // when
        Throwable result = catchThrowable(() -> locationService.createLocation(
                null, "53.45", "14.54", "    ", "Zachodniopomorskie"
        ));
        // then
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
