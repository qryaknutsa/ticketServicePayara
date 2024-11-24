package com.example.ticketServicePayara.dao;


import com.example.ticketServicePayara.dto.TicketWriteUpdate;
import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import com.example.ticketServicePayara.enums.TicketType;
import com.example.ticketServicePayara.exception.*;
import com.example.ticketServicePayara.model.Location;
import com.example.ticketServicePayara.model.Person;
import com.example.ticketServicePayara.model.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
            throw new TicketNotFoundException("По вашему запросу билеты не найдены.");
        return paginatedList;
    }


    public Ticket getById(int id) {
        if (id < 0) throw new InvalidParameterException("Значение id должно быть больше нуля");

        Ticket ticket = entityManager.find(Ticket.class, id);

        if (ticket == null) throw new TicketNotFoundException("По вашему запросу билет не найден.");
        else return ticket;
    }


    public Person getPersonById(int id) {
        if (id < 0) throw new InvalidParameterException("Значение id должно быть больше нуля");

        Person person = entityManager.find(Person.class, id);

        if (person == null) throw new TicketNotFoundException("По вашему запросу билет не найден.");
        else return person;
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
    public void update(int id, TicketWriteUpdate newTicket) {
        Ticket oldTicket = getById(id);
        if (newTicket.getName() != null) oldTicket.setName(newTicket.getName());
        if (newTicket.getPrice() != null) oldTicket.setPrice(newTicket.getPrice());
        if (newTicket.getDiscount() != null) oldTicket.setDiscount(newTicket.getDiscount());
        if (newTicket.getRefundable() != null) oldTicket.setRefundable(newTicket.getRefundable());
        if (newTicket.getType() != null) oldTicket.setType(TicketType.valueOf(newTicket.getType()));

        if (newTicket.getCoordinates() != null) {
            if (newTicket.getCoordinates().getX() != null)
                oldTicket.getCoordinates().setX(newTicket.getCoordinates().getX());
            if (newTicket.getCoordinates().getY() != null)
                oldTicket.getCoordinates().setY(newTicket.getCoordinates().getY());
        }

        if (newTicket.getPerson() != null) {
            if (oldTicket.getPerson() == null) {
                if (newTicket.getPerson().getHeight() != null &&
                        newTicket.getPerson().getHairColor() != null &&
                        newTicket.getPerson().getLocation() != null &&
                        newTicket.getPerson().getLocation().getX() != null &&
                        newTicket.getPerson().getLocation().getZ() != null) {
                    oldTicket.setPerson(new Person());
                    oldTicket.getPerson().setHeight(newTicket.getPerson().getHeight());
                    oldTicket.getPerson().setHairColor(HairColor.valueOf(newTicket.getPerson().getHairColor()));
                    if (newTicket.getPerson().getEyeColor() != null)
                        oldTicket.getPerson().setEyeColor(EyeColor.valueOf(newTicket.getPerson().getEyeColor()));
                    if (newTicket.getPerson().getNationality() != null)
                        oldTicket.getPerson().setNationality(Country.valueOf(newTicket.getPerson().getNationality()));

                    oldTicket.getPerson().setLocation(new Location());
                    oldTicket.getPerson().getLocation().setX(newTicket.getPerson().getLocation().getX());
                    oldTicket.getPerson().getLocation().setZ(newTicket.getPerson().getLocation().getZ());

                    if (newTicket.getPerson().getLocation().getY() != null)
                        oldTicket.getPerson().getLocation().setY(newTicket.getPerson().getLocation().getY());
                    if (newTicket.getPerson().getLocation().getName() != null)
                        oldTicket.getPerson().getLocation().setName(newTicket.getPerson().getLocation().getName());
                } else
                    throw new RuntimeException("Плохой update, не хватает чего-то для создания норм person"); //TODO: создать exception и обработать его
            } else {
                if (newTicket.getPerson().getHeight() != null)
                    oldTicket.getPerson().setHeight(newTicket.getPerson().getHeight());
                if (newTicket.getPerson().getEyeColor() != null)
                    oldTicket.getPerson().setEyeColor(EyeColor.valueOf(newTicket.getPerson().getEyeColor()));
                if (newTicket.getPerson().getHairColor() != null)
                    oldTicket.getPerson().setHairColor(HairColor.valueOf(newTicket.getPerson().getHairColor()));
                if (newTicket.getPerson().getNationality() != null)
                    oldTicket.getPerson().setNationality(Country.valueOf(newTicket.getPerson().getNationality()));

                if (newTicket.getPerson().getLocation().getX() != null)
                    oldTicket.getPerson().getLocation().setX(newTicket.getPerson().getLocation().getX());
                if (newTicket.getPerson().getLocation().getZ() != null)
                    oldTicket.getPerson().getLocation().setZ(newTicket.getPerson().getLocation().getZ());
                if (newTicket.getPerson().getLocation().getY() != null)
                    oldTicket.getPerson().getLocation().setY(newTicket.getPerson().getLocation().getY());
                if (newTicket.getPerson().getLocation().getName() != null)
                    oldTicket.getPerson().getLocation().setName(newTicket.getPerson().getLocation().getName());

            }
        }
        entityManager.merge(oldTicket);
    }


    @Transactional
    public void deleteById(int id) {
        String deleteEventTicketSql = "DELETE FROM event_ticket WHERE ticket_id = " + id;
        entityManager.createNativeQuery(deleteEventTicketSql).executeUpdate();

        Ticket ticket = entityManager.find(Ticket.class, id);
        if (ticket != null) {
            entityManager.remove(ticket);
        }
    }



    public double discountSum() {
        return getAll().stream()
                .mapToDouble(Ticket::getDiscount)
                .sum();
    }

    public long getAmountLessThanType(String type) {
        try {
            TicketType t = TicketType.fromValue(type.toUpperCase());
            List<TicketType> list = TicketType.getLessTypes(t);
            long num = 0;
            List<Ticket> tickets = getAll();

            for (Ticket ticket : tickets) {
                if (ticket.getType() == null || list.contains(ticket.getType()))  num++;
            }
            return num;
        } catch (IllegalArgumentException e) {
            TypeMismatchException q = new TypeMismatchException(type, TicketType.class);
            q.initPropertyName("type");
            throw q;
        }
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

                try {
                    return switch (operator) {
                        case "=" -> fieldValue != null && fieldValue.toString().equals(parseValue(value, f.getType()));
                        case "!=" -> !fieldValue.toString().equals(parseValue(value, f.getType()));
                        case ">" -> ((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) > 0;
                        case "<" -> ((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) < 0;
                        case ">=" -> ((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) >= 0;
                        case "<=" -> ((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) <= 0;
                        case "contains" -> fieldValue.toString().contains(value);
                        default -> throw new NoFilterMethodException(operator);
                    };
                } catch (NumberFormatException e) {
                    TypeMismatchException q = new TypeMismatchException(value, f.getType());
                    q.initPropertyName(f.getName());
                    throw q;
                }

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
