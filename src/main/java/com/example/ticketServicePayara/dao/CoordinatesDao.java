package com.example.ticketServicePayara.dao;

import com.example.ticketServicePayara.model.Coordinates;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class CoordinatesDao {
    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager entityManager;


    public List<Coordinates> getAll(){
        return entityManager.createQuery("SELECT l FROM Coordinates l", Coordinates.class).getResultList();
    }
    public Coordinates getById(int id){
        return entityManager.find(Coordinates.class, id);
    }
    public Coordinates save(Coordinates entity){
        entityManager.persist(entity);
        return entity;
    }
    public void update(Coordinates entity){
        entityManager.merge(entity);
    }
    public void deleteById(int id){
        Coordinates coordinates = getById(id);
        if (coordinates != null) {
            entityManager.remove(coordinates);
        }
    }
    public void delete(Coordinates entity){
        entityManager.remove(entity);
    }
}
