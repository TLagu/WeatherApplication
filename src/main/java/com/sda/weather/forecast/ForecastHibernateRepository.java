package com.sda.weather.forecast;

import com.sda.weather.location.Location;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class ForecastHibernateRepository implements ForecastRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Optional<Forecast> getByLocationAndCreatedDateAndForecastDate(Location location, Instant creationDate, Instant forecastDate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String hql_query = "from Forecast f where f.location = :location and f.creationDate > :creationDate and f.forecastDate = :forecastDate";
        @SuppressWarnings("unchecked")
        List<Forecast> forecasts = session.createQuery(hql_query)
                .setParameter("location", location)
                .setParameter("creationDate", creationDate)
                .setParameter("forecastDate", forecastDate)
                .getResultList();
        Optional<Forecast> forecast = Optional.ofNullable((forecasts.size() > 0)?forecasts.get(0):null);
        transaction.commit();
        session.close();
        return forecast;
    }

    @Override
    public Forecast save(Forecast forecast) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(forecast);
        transaction.commit();
        session.close();
        return forecast;
    }
}
