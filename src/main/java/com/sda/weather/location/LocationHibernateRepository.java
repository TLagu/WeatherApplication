package com.sda.weather.location;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class LocationHibernateRepository implements LocationRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Location save(Location location) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(location);
        transaction.commit();
        session.close();
        return location;
    }

    @Override
    public List<Location> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Location> locations = session.createQuery("FROM Location e", Location.class).getResultList();
        transaction.commit();
        session.close();
        return locations;
    }
}
