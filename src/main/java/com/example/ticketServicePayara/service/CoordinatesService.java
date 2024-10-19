package com.example.ticketServicePayara.service;

import com.example.ticketServicePayara.dto.CoordinatesDto;
import com.example.ticketServicePayara.mappers.CoordinatesMapper;
import com.example.ticketServicePayara.model.Coordinates;
import com.example.ticketServicePayara.dao.CoordinatesDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinatesService {
    private final CoordinatesDao coordinatesDao;

    public List<Coordinates> getAll(){
        return coordinatesDao.getAll();
    }

    public CoordinatesDto getById(int id) throws NotActiveException {
        return CoordinatesMapper.toCoordinatesDto(coordinatesDao.getById(id));
    }

    public CoordinatesDto save(CoordinatesDto coordinatesDto) throws NotActiveException {
        return CoordinatesMapper.toCoordinatesDto(coordinatesDao.save(CoordinatesMapper.toCoordinates(coordinatesDto)));
    }


    public void deleteById(int id) throws NotActiveException {
        coordinatesDao.deleteById(id);
    }

    public void delete(CoordinatesDto coordinatesDto) throws NotActiveException {
        coordinatesDao.delete(CoordinatesMapper.toCoordinates(coordinatesDto));
    }

}
