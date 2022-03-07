package com.sda.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.forecast.ForecastController;
import com.sda.weather.forecast.ForecastHibernateRepository;
import com.sda.weather.forecast.ForecastRepository;
import com.sda.weather.forecast.ForecastService;
import com.sda.weather.location.LocationController;
import com.sda.weather.location.LocationRepository;
import com.sda.weather.location.LocationService;
import com.sda.weather.location.LocationHibernateRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Application {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        ObjectMapper objectMapper = new ObjectMapper();
        // doesn't fail when our DTO object structure is not the same as a JSON structure
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        LocationRepository locationRepository = new LocationHibernateRepository(sessionFactory);
        LocationService locationService = new LocationService(locationRepository);
        LocationController locationController = new LocationController(locationService, objectMapper);
        ForecastRepository forecastRepository = new ForecastHibernateRepository(sessionFactory);
        ForecastService forecastService = new ForecastService(locationService, forecastRepository);
        ForecastController forecastController = new ForecastController(forecastService, objectMapper);
        UserInterface userInterface = new UserInterface(locationController, forecastController);
        userInterface.run();
        sessionFactory.close();
        registry.close();
    }
}
