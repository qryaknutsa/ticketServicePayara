package org.example.ticketservice.service;


import org.example.ticketservice.model.Coordinates;
import org.example.ticketservice.repo.CoordinatesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class CoordinatesService {
    @Autowired
    private CoordinatesRepo coordinatesRepo;

    public List<Coordinates> getAll() {
        return coordinatesRepo.findAll();
    }

    public Coordinates getById(int id) {
        if (coordinatesRepo.findById(id).isPresent()) return coordinatesRepo.findById(id).orElseThrow();
        else return null;

    }

    @Transactional
    public Coordinates save(Coordinates entity) {
        coordinatesRepo.save(entity);
        return entity;
    }

    @Transactional
    public void update(Coordinates coordinates, Map<String, Object> updates) {
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
        coordinatesRepo.save(coordinates);
    }

    @Transactional
    public void deleteById(int id) {
        coordinatesRepo.deleteById(id);

    }

    @Transactional
    public void delete(Coordinates entity) {
        coordinatesRepo.delete(entity);
    }
}
