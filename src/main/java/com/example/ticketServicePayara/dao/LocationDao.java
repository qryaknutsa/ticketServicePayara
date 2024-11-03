package com.example.ticketServicePayara.dao;


import com.example.ticketServicePayara.model.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class LocationDao {
    @PersistenceContext
    private EntityManager entityManager;


    public List<Location> getAll(){
        return entityManager.createQuery("SELECT l FROM Location l", Location.class).getResultList();
    }
    public Location getById(int id){
        return entityManager.find(Location.class, id);
    }

    @Transactional
    public Location save(Location entity){
        entityManager.persist(entity);
        return entity;
    }
    @Transactional
    public void update(Location location, Map<String, Object> updates){
        updates.forEach((field, value) -> {
            switch (field) {
                case "x":
                    location.setX((int) value);
                    break;
                case "y":
                    location.setY((long) value);
                    break;
                case "z":
                    location.setZ((double) value);
                    break;
                case "name":
                    location.setName((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field in location: " + field);
            }
        });
        entityManager.merge(location);
    }

    @Transactional
    public void deleteById(int id){
        Location location = getById(id);
        if (location != null) {
            entityManager.remove(location);
        }
    }
    @Transactional
    public void delete(Location entity){
        entityManager.remove(entity);
    }
}
