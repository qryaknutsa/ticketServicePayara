package com.example.ticketServicePayara.dao;


import com.example.ticketServicePayara.enums.TicketType;
import com.example.ticketServicePayara.model.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TicketDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CoordinatesDao coordinatesDao;
    @Autowired
    private PersonDao personDao;

    public List<Ticket> getAll(){
        return entityManager.createQuery("SELECT l FROM Ticket l", Ticket.class).getResultList();
    }
    public Ticket getById(int id){
        return entityManager.find(Ticket.class, id);
    }
    @Transactional
    public Ticket save(Ticket entity){
        coordinatesDao.save(entity.getCoordinates());
        if(entity.getPerson()!=null)
            personDao.save(entity.getPerson());

        entityManager.persist(entity);
        return entity;
    }
    @Transactional
    public void update(int id, Map<String, Object> updates){
        Ticket ticket = getById(id);

        updates.forEach((field, value) -> {
            switch (field) {
                case "name":
                    ticket.setName(value.toString());
                    break;
                case "coordinates":
                    coordinatesDao.update(ticket.getCoordinates(), (Map<String, Object>) value);
                    break;
                case "price":
                    ticket.setPrice((int) value);
                    break;
                case "discount":
                    ticket.setDiscount((double) value);
                    break;
                case "refundable":
                    ticket.setRefundable((Boolean) value);
                    break;
                case "type":
                    ticket.setType(TicketType.fromValue(value.toString()));
                    break;
                case "person":
                    personDao.update(ticket.getPerson(), (Map<String, Object>) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + field);
            }
        });

        entityManager.merge(ticket);    }
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

    // Доп операции
    public double discountSum() {
        return getAll().stream()
                .mapToDouble(Ticket::getDiscount)
                .sum();
    }

    public long getAmountLessThanType(String type){
        TicketType t = TicketType.fromValue(type);
        List<TicketType> list = TicketType.getLessTypes(t);
        return getAll().stream().filter(ticket -> list.contains(ticket.getType())).count();
    }

    public Set<String> getUniqueTypes(){
        return getAll().stream()
                .map(ticket -> ticket.getType().name())
                .collect(Collectors.toSet());
    }


}
