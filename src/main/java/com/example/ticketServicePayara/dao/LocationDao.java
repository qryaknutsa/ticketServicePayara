package com.example.ticketServicePayara.dao;

import com.example.ticketServicePayara.model.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class LocationDao {
    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager entityManager;


    public List<Location> getAll(){
        return entityManager.createQuery("SELECT l FROM Location l", Location.class).getResultList();
    }
    public Location getById(int id){
        return entityManager.find(Location.class, id);
    }
    public Location save(Location entity){
        entityManager.persist(entity);
        return entity;
    }
    public void update(Location entity){
        entityManager.merge(entity);
    }
    public void deleteById(int id){
        Location location = getById(id);
        if (location != null) {
            entityManager.remove(location);
        }
    }
    public void delete(Location entity){
        entityManager.remove(entity);
    }
}
