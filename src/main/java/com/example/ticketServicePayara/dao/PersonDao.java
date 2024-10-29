package com.example.ticketServicePayara.dao;


import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import com.example.ticketServicePayara.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class PersonDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LocationDao locationDao;


    public List<Person> getAll(){
        return entityManager.createQuery("SELECT l FROM Person l", Person.class).getResultList();
    }
    public Person getById(int id){
        return entityManager.find(Person.class, id);
    }
    @Transactional
    public Person save(Person entity){
        locationDao.save(entity.getLocation());
        entityManager.persist(entity);
        return entity;
    }
    @Transactional
    public void update(Person person, Map<String, Object> updates){
        updates.forEach((field, value) -> {
            switch (field) {
                case "height":
                    person.setHeight((int) value);
                    break;
                case "eyeColor":
                    person.setEyeColor(EyeColor.fromValue(value.toString().toUpperCase()));
                    break;
                case "hairColor":
                    person.setHairColor(HairColor.fromValue(value.toString().toUpperCase()));
                    break;
                case "nationality":
                    person.setNationality(Country.fromValue(value.toString().toUpperCase()));
                    break;
                case "location":
                    locationDao.update(person.getLocation(), (Map<String, Object>) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field in person: " + field);
            }
        });
        entityManager.merge(person);
    }
    @Transactional
    public void deleteById(int id){
        Person location = getById(id);
        if (location != null) {
            entityManager.remove(location);
        }
    }
    @Transactional
    public void delete(Person entity){
        entityManager.remove(entity);
    }
}
