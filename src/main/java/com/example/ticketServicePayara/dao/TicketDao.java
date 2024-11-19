package com.example.ticketServicePayara.dao;


import com.example.ticketServicePayara.enums.TicketType;
import com.example.ticketServicePayara.exception.*;
import com.example.ticketServicePayara.model.Coordinates;
import com.example.ticketServicePayara.model.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TicketDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CoordinatesDao coordinatesDao;
    @Autowired
    private PersonDao personDao;

    public List<Ticket> getAll() {
        return entityManager.createQuery("SELECT l FROM Ticket l", Ticket.class).getResultList();
    }

    public List<Ticket> getAllFilteredSortedPaginated(Integer size, Integer page, String sort, String filter) {
        if (size == null) size = 10;
        if (page == null) page = 0;
        else page--;

        List<Ticket> list = getAll();
        List<Ticket> filteredList = filterTickets(filter, list);
        List<Ticket> sortedList = sortTickets(sort, filteredList);


        int fromIndex = Math.min(size * page, sortedList.size());
        int toIndex = Math.min(fromIndex + size, sortedList.size());
        List<Ticket> paginatedList = sortedList.subList(fromIndex, toIndex);
        if (paginatedList.isEmpty())
            throw new TicketNotFoundException();
        return paginatedList;
    }


    public Ticket getById(int id) {
        if (id < 0) throw new InvalidParameterException("Значение id должно быть больше нуля");

        Ticket ticket = entityManager.find(Ticket.class, id);

        if (ticket == null) throw new TicketNotFoundException();
        else return ticket;

    }

    @Transactional
    public Ticket save(Ticket entity) {
        coordinatesDao.save(entity.getCoordinates());
        if (entity.getPerson() != null)
            personDao.save(entity.getPerson());

        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public void update(int id, Map<String, Object> updates) {
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

        entityManager.merge(ticket);
    }

    @Transactional
    public void deleteById(int id) {
        Ticket ticket = getById(id);
        delete(ticket);
    }


    @Transactional
    public void delete(Ticket entity) {
        entityManager.remove(entity);
    }

    @Transactional
    public void deleteByIds(List<Integer> ids) {
        for (int id : ids) deleteById(id);
    }

    // Доп операции
    public double discountSum() {
        return getAll().stream()
                .mapToDouble(Ticket::getDiscount)
                .sum();
    }

    public long getAmountLessThanType(String type) {
        TicketType t = TicketType.fromValue(type);
        List<TicketType> list = TicketType.getLessTypes(t);
        return getAll().stream().filter(ticket -> list.contains(ticket.getType())).count();
    }

    public Set<String> getUniqueTypes() {
        Set<String> types = new HashSet<>();
        List<Ticket> list = getAll();
        for (Ticket ticket : list) {
            if (ticket.getType() != null) {
                types.add(ticket.getType().getType());
            }
        }
        return types;
    }

    private List<Ticket> sortTickets(String sort, List<Ticket> list) {
        if (sort != null && !sort.isEmpty()) {
            Comparator<Ticket> comparator = null;

            String[] sortConditions = sort.split(",");
            for (String sortCondition : sortConditions) {
                String[] parts = sortCondition.split(":");
                String field = parts[0].trim();
                String direction = (parts.length > 1) ? parts[1].trim().toLowerCase() : "asc";

                Comparator<Ticket> fieldComparator = createComparator(field, direction);
                comparator = (comparator == null) ? fieldComparator : comparator.thenComparing(fieldComparator);
            }

            if (comparator != null) {
                return list.stream().sorted(comparator).toList();
            }
        } else {
            Comparator<Ticket> fieldComparator = createComparator("id", "asc");
            return list.stream().sorted(fieldComparator).toList();
        }
        return list;
    }

    private Comparator<Ticket> createComparator(String field, String method) {
        Comparator<Ticket> comparator;
        try {
            Field f = Ticket.class.getDeclaredField(field);
            f.setAccessible(true);
            comparator = Comparator.comparing(ticket -> {
                try {
                    return (Comparable) f.get(ticket);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field: " + field, e);
                }
            });
            if (method.equals("desc")) return comparator.reversed();
            else if (method.equals("asc")) return comparator;
            else throw new NoSortMethodException(method);
        } catch (NoSuchFieldException e) {
            throw new NoFieldException(field);
        }
    }


    private List<Ticket> filterTickets(String filter, List<Ticket> list) {
        if (filter != null && !filter.isEmpty()) {
            String[] conditions = filter.split(",");
            for (String condition : conditions) {
                if (condition.contains(">=")) {
                    String[] parts = condition.split(">=");
                    list = applyFilter(list, parts[0].trim(), ">=", parts[1].trim());
                } else if (condition.contains("<=")) {
                    String[] parts = condition.split("<=");
                    list = applyFilter(list, parts[0].trim(), "<=", parts[1].trim());
                } else if (condition.contains(">")) {
                    String[] parts = condition.split(">");
                    list = applyFilter(list, parts[0].trim(), ">", parts[1].trim());
                } else if (condition.contains("<")) {
                    String[] parts = condition.split("<");
                    list = applyFilter(list, parts[0].trim(), "<", parts[1].trim());
                } else if (condition.contains("!=")) {
                    String[] parts = condition.split("!=");
                    list = applyFilter(list, parts[0].trim(), "!=", parts[1].trim());
                } else if (condition.contains("=")) {
                    String[] parts = condition.split("=");
                    list = applyFilter(list, parts[0].trim(), "=", parts[1].trim());
                } else if (condition.contains("contains")) {
                    String[] parts = condition.split("contains");
                    list = applyFilter(list, parts[0].trim(), "contains", parts[1].trim());
                } else {
                    throw new NoFilterMethodException(condition);
                }
            }

        }
        return list;
    }


    private List<Ticket> applyFilter(List<Ticket> tickets, String field, String operator, String value) {
        return tickets.stream().filter(ticket -> {
            try {
                Object fieldValue;
                Field f;
                String[] fieldParts = field.split("\\.");
                if (fieldParts.length > 1) {
                    Object[] values = getNestedFieldValue(ticket, fieldParts);
                    fieldValue = values[0];
                    f = (Field) values[1];
                } else {
                    // Обработка простых полей
                    f = Ticket.class.getDeclaredField(field);
                    f.setAccessible(true);
                    fieldValue = f.get(ticket);
                }

                if (fieldValue == null && !("=").equals(operator)) {
                    return false;
                }

                return switch (operator) {
                    case "=" -> fieldValue != null && fieldValue.toString().equals(value);
                    case "!=" -> !fieldValue.toString().equals(value);
                    case ">" -> ((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) > 0;
                    case "<" -> ((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) < 0;
                    case ">=" -> ((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) >= 0;
                    case "<=" -> ((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) <= 0;
                    case "contains" -> fieldValue.toString().contains(value);
                    default -> throw new NoFilterMethodException(operator);
                };
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new NoFieldException(field);
            }
        }).collect(Collectors.toList());
    }

    private Object[] getNestedFieldValue(Ticket ticket, String[] fieldParts) throws NoSuchFieldException, IllegalAccessException {
        Object currentObject = ticket;
        for (int i = 0; i < fieldParts.length - 1; i++) {
            Field field = currentObject.getClass().getDeclaredField(fieldParts[i]);
            field.setAccessible(true);
            currentObject = field.get(currentObject);
            if (currentObject == null) {
                return null;
            }
        }
        Field finalField = currentObject.getClass().getDeclaredField(fieldParts[fieldParts.length - 1]);
        finalField.setAccessible(true);
        return new Object[]{finalField.get(currentObject), finalField};
    }

    private Object parseValue(String value, Class<?> targetType) {
        if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (Enum.class.isAssignableFrom(targetType)) {
            return Enum.valueOf((Class<Enum>) targetType, value);
        } else {
            return value;
        }
    }

}
