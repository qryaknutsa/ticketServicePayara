package com.example.ticketServicePayara.converter;

import com.example.ticketServicePayara.dto.LocationWrite;
import com.example.ticketServicePayara.model.Location;

public class LocationWriteConverter {

    public static Location toLocation(LocationWrite locationWrite) {
        Location location = new Location();
        if (locationWrite.getName() != null) location.setName(locationWrite.getName());
        location.setX(locationWrite.getX());
        location.setY(locationWrite.getY());
        location.setZ(locationWrite.getZ());
        return location;
    }

    public static LocationWrite toLocationWrite(Location location) {
        LocationWrite locationWrite = new LocationWrite();
        if (location.getName() != null) locationWrite.setName(location.getName());
        locationWrite.setX(location.getX());
        locationWrite.setY(location.getY());
        locationWrite.setZ(location.getZ());
        return locationWrite;
    }
}
