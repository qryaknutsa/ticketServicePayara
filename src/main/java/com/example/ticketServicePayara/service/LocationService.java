package com.example.ticketServicePayara.service;

import com.example.ticketServicePayara.dto.LocationDto;
import com.example.ticketServicePayara.mappers.LocationMapper;
import com.example.ticketServicePayara.dao.LocationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationDao locationRepo;

    public List<LocationDto> findAll() {
        return locationRepo.getAll().stream().map(LocationMapper::toLocationDto).toList();
    }

    public LocationDto findById(int id) throws NotActiveException {
        return LocationMapper.toLocationDto(locationRepo.getById(id));
    }

    public LocationDto save(LocationDto locationDto) {
        return LocationMapper.toLocationDto(locationRepo.save(LocationMapper.toLocation(locationDto)));
    }

    public void deleteById(int id) {
        locationRepo.deleteById(id);
    }

    public void delete(LocationDto locationDto) {
        locationRepo.delete(LocationMapper.toLocation(locationDto));
    }
}