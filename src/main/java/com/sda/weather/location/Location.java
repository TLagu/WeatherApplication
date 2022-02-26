package com.sda.weather.location;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String locality;

    private Float longitude;

    private Float latitude;

    @Column(nullable = false)
    private String country;

    private String region;

}
