package com.example.ticketServicePayara.converter;

import com.example.ticketServicePayara.dto.CoordinatesWrite;
import com.example.ticketServicePayara.model.Coordinates;

public class CoordinatesWriteConverter {
    public static Coordinates toCoordinates(CoordinatesWrite coordinatesWrite) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(coordinatesWrite.getX());
        coordinates.setY(coordinatesWrite.getY());
        return coordinates;
    }

    public static CoordinatesWrite toCoordinatesWrite(Coordinates coordinatesWrite) {
        CoordinatesWrite coordinates = new CoordinatesWrite();
        coordinates.setX(coordinatesWrite.getX());
        coordinates.setY(coordinatesWrite.getY());
        return coordinates;
    }

}
