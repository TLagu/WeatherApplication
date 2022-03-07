package com.sda.weather.forecast;

import com.sda.weather.location.Location;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "forecast")
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Float temperature;

    private Integer humidity;

    @Column(name = "wind_speed")
    private Integer windSpeed;

    @Column(name = "wind_direction")
    private WindDirection windDirection;

    private Integer pressure;

    @ManyToOne
    private Location location;

    @Column(name = "forecast_date")
    private Instant forecastDate;

    @Column(name = "creation_date")
    private Instant creationDate;
}
