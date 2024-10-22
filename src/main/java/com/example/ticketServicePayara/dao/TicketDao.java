package com.example.ticketServicePayara.dao;

import com.example.ticketServicePayara.model.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//TODO: filter and sort by multiple fields
@Service
public class TicketDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Ticket> getAll(){
        return entityManager.createQuery("SELECT l FROM Ticket l", Ticket.class).getResultList();
    }
    public Ticket getById(int id){
        return entityManager.find(Ticket.class, id);
    }
    @Transactional
    public Ticket save(Ticket entity){
        entityManager.persist(entity);
        return entity;
    }
    @Transactional
    public void update(Ticket entity){
        entityManager.merge(entity);
    }
    @Transactional
    public void deleteById(int id){
        Ticket location = getById(id);
        if (location != null) {
            entityManager.remove(location);
        }
    }
    @Transactional
    public void delete(Ticket entity){
        entityManager.remove(entity);
    }


//    @Query("SELECT DISTINCT t.type FROM Ticket t")
//    List<Ticket.TicketType> findDistinctTicketTypes();

}
