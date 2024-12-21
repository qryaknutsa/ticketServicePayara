package org.example.ticketservice.service;


import org.example.ticketservice.model.Location;
import org.example.ticketservice.repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class LocationService {
    @Autowired
    private LocationRepo locationRepo;

    public List<Location> getAll(){
        return locationRepo.findAll();
    }
    public Location getById(int id){
        if(locationRepo.findById(id).isPresent()) return locationRepo.findById(id).get();
        else return null;
    }

    @Transactional
    public Location save(Location entity){
        locationRepo.save(entity);
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
        locationRepo.save(location);
    }

    @Transactional
    public void deleteById(int id){
        locationRepo.deleteById(id);
    }
    @Transactional
    public void delete(Location entity){
        locationRepo.delete(entity);
    }
}
