//package com.example.ticketServicePayara.dao;
//
//import com.example.ticketServicePayara.model.Coordinates;
//import com.example.ticketServicePayara.model.Event;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//
//public class EventDao {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Event> getAll(){
//        return entityManager.createQuery("SELECT l FROM Event l", Event.class).getResultList();
//    }
//    public Event getById(int id){
//        return entityManager.find(Event.class, id);
//    }
//    @Transactional
//    public Event save(Event entity){
//        entityManager.persist(entity);
//        return entity;
//    }
//}
