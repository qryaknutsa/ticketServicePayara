package com.example.ticketServicePayara.dao;

import com.example.ticketServicePayara.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class PersonDao {
    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager entityManager;


    public List<Person> getAll(){
        return entityManager.createQuery("SELECT l FROM Person l", Person.class).getResultList();
    }
    public Person getById(int id){
        return entityManager.find(Person.class, id);
    }
    public Person save(Person entity){
        entityManager.persist(entity);
        return entity;
    }
    public void update(Person entity){
        entityManager.merge(entity);
    }
    public void deleteById(int id){
        Person location = getById(id);
        if (location != null) {
            entityManager.remove(location);
        }
    }
    public void delete(Person entity){
        entityManager.remove(entity);
    }
}
