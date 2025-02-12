package com.example.ticketServicePayara.dao;


import com.example.ticketServicePayara.model.Coordinates;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class CoordinatesDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Coordinates> getAll(){
        return entityManager.createQuery("SELECT l FROM Coordinates l", Coordinates.class).getResultList();
    }
    public Coordinates getById(int id){
        return entityManager.find(Coordinates.class, id);
    }
    @Transactional
    public Coordinates save(Coordinates entity){
        entityManager.persist(entity);
        return entity;
    }
    @Transactional
    public void update(Coordinates coordinates, Map<String, Object> updates){
        updates.forEach((field, value) -> {
            switch (field) {
                case "x":
                    coordinates.setX((float) value);
                    break;
                case "y":
                    coordinates.setY((Float) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field in coordinates: " + field);
            }
        });
        entityManager.merge(coordinates);
    }
    @Transactional
    public void deleteById(int id){
        Coordinates coordinates = getById(id);
        if (coordinates != null) {
            entityManager.remove(coordinates);
        }
    }
    @Transactional
    public void delete(Coordinates entity){
        entityManager.remove(entity);
    }
}
