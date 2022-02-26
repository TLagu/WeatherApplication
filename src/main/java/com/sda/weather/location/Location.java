package com.sda.weather.location;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "location")
public class Location {

    @Id
    @Column(name = "id", nullable = false) // todo you can get rid of these properties
    // todo generation strategy
    private Long id;

    @Column(nullable = false)
    private String locality;

    private String coords; // todo change to specific fields (longitude and latitude)

    @Column(nullable = false) // todo are you sure?
    private String region;

    private String country;

    private Instant createdDate; // todo this is unnecessary

    public Long getId() { // todo you can use the Lombook
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
