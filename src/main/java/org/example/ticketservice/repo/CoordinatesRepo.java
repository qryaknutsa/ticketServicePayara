package org.example.ticketservice.repo;

import org.example.ticketservice.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepo extends JpaRepository<Coordinates, Integer> {
}
