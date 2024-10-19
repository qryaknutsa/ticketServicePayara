package com.example.ticketServicePayara.mappers;

import com.example.ticketServicePayara.dto.LocationDto;
import com.example.ticketServicePayara.model.Location;

public class LocationMapper {

    public static LocationDto toLocationDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setX(location.getX());
        locationDto.setZ(location.getZ());
        locationDto.setY(location.getY());
        if(location.getName() != null) locationDto.setName(location.getName());

        return locationDto;
    }

    public static Location toLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setX(locationDto.getX());
        location.setZ(locationDto.getZ());
        location.setY(locationDto.getY());
        if(locationDto.getName() != null) location.setName(locationDto.getName());

        return location;
    }
}
