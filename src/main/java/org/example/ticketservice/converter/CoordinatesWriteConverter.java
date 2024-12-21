package org.example.ticketservice.converter;


import org.example.ticketservice.dto.CoordinatesWrite;
import org.example.ticketservice.model.Coordinates;

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
