package org.example.ticketservice.service;

import org.example.ticketservice.exception.TicketNotFoundException;
import org.example.ticketservice.model.Person;
import org.example.ticketservice.enums.*;
import org.example.ticketservice.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private LocationService locationService;


    public List<Person> getAll(){
        return personRepo.findAll();
    }
    public Person getById(int id){
        if(personRepo.findById(id).isPresent()) return personRepo.findById(id).get();
        else throw new TicketNotFoundException("Человек с id = " + id + " не найден.");
    }
    @Transactional
    public Person save(Person entity){
//        locationService.save(entity.getLocation());
        personRepo.save(entity);
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
                    locationService.update(person.getLocation(), (Map<String, Object>) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field in person: " + field);
            }
        });
        personRepo.save(person);
    }
    @Transactional
    public void deleteById(int id){
        personRepo.deleteById(id);
    }
    @Transactional
    public void delete(Person entity){
        personRepo.delete(entity);
    }
}
