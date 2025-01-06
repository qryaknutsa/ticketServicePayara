package org.example.ticketservice.service;


import org.example.ticketservice.enums.*;
import org.example.ticketservice.model.*;
import org.example.ticketservice.exception.*;
import org.example.ticketservice.dto.*;
import org.example.ticketservice.repo.TicketRepo;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@Service
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private CoordinatesService coordinatesService;
    @Autowired
    private PersonService personService;

    public List<Ticket> getAll() {
        return ticketRepo.findAll();
    }

    public long getNumAllByEventId(int eventId) {
        return ticketRepo.findAll().stream().filter(ticket -> ticket.getEventId() == eventId).count();
    }


    public List<Ticket> getAllFilteredSortedPaginated(Integer size, Integer page, String sort, String filter) {
        if (size == null) size = 10;
        if (page == null) page = 0;
        else page--;

        isIntegerPositive(size, "size");
        isIntegerPositive(page, "page");

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
        isIntegerPositive(id, "id");
        if(ticketRepo.findById(id).isPresent()) return ticketRepo.findById(id).get();
        else return null;
    }


    public Person getPersonById(int id) {
        isIntegerPositive(id, "id");
        return personService.getById(id);
    }

    @Transactional
    public Ticket save(Ticket entity) {
//        coordinatesService.save(entity.getCoordinates());
//        if (entity.getPerson() != null)
//            personService.save(entity.getPerson());
        ticketRepo.save(entity);
        return entity;
    }


    @Transactional
    public List<Long> saveTickets(Ticket entity, int num) {
        isIntegerPositive(num, "число билетов");
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Ticket newTicket = new Ticket(entity);
            ticketRepo.save(newTicket);
            ids.add(newTicket.getId());
        }
        return ids;
    }


    @Transactional
    public Ticket update(int id, TicketWriteUpdate newTicket) {
        isIntegerPositive(id, "id");
        Ticket oldTicket = getById(id);
        updateNonObjectFields(oldTicket, newTicket);
        updateCoordinates(oldTicket, newTicket);
        updatePerson(oldTicket, newTicket);
        ticketRepo.save(oldTicket);
        return oldTicket;
    }


    @Transactional
    public void deleteById(int id) {
        isIntegerPositive(id, "id");
        if(ticketRepo.findById(id).isPresent()) ticketRepo.deleteById(id);
        else throw new TicketNotFoundException("Билета с данным id нет");
    }

    @Transactional
    public void deleteTicketsByEventIds(int id) {
        isIntegerPositive(id, "event id");
        List<Long> ids = getAll().stream().filter(ticket -> ticket.getEventId() == id).map(ticket -> ticket.getId()).toList();
        for (Long ticketId : ids) {
            deleteById(ticketId.intValue());
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
                if (ticket.getType() == null || list.contains(ticket.getType())) num++;
            }
            return num;
        } catch (IllegalArgumentException e) {
            TypeMismatchException q = new TypeMismatchException(type, TicketType.class);
            q.initPropertyName("ticket type");
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
            comparator = Comparator.comparing(ticket -> {
                try {
                    return (Comparable) getNestedFieldValueForSort(ticket, field);
                } catch (Exception e) {
                    throw new RuntimeException("Error accessing field: " + field, e);
                }
            }, Comparator.nullsFirst(Comparator.naturalOrder()));
            if (method.equals("desc")) return comparator.reversed();
            else if (method.equals("asc")) return comparator;
            else throw new NoSortMethodException(method);
        } catch (Exception e) {
            throw new NoSortMethodException(method);
        }
    }

    private Object getNestedFieldValueForSort(Object obj, String fieldPath) throws Exception {
        String[] fields = fieldPath.split("\\.");
        Object currentObject = obj;

        for (String fieldName : fields) {
            if (currentObject == null) {
                return null;
            }

            Field field = currentObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            currentObject = field.get(currentObject);
        }

        return currentObject;
    }


    private List<Ticket> filterTickets(String filter, List<Ticket> list) {
        if (filter != null && !filter.isEmpty()) {
            String[] conditions = filter.split(",");
            for (String condition : conditions) {
                if (condition.contains(">=")) list = checkAndApplyFilter(condition, ">=", list);
                else if (condition.contains("<=")) list = checkAndApplyFilter(condition, "<=", list);
                else if (condition.contains(">")) list = checkAndApplyFilter(condition, ">", list);
                else if (condition.contains("<")) list = checkAndApplyFilter(condition, "<", list);
                else if (condition.contains("!=")) list = checkAndApplyFilter(condition, "!=", list);
                else if (condition.contains("=")) list = checkAndApplyFilter(condition, "=", list);
                else if (condition.contains("contains")) list = checkAndApplyFilter(condition, "contains", list);
                else throw new NoFilterMethodException(condition);
            }
        }
        return list;
    }

    private List<Ticket> checkAndApplyFilter(String condition, String filterMethod, List<Ticket> list) {
        String[] parts = condition.split(filterMethod);
        if (parts.length < 2) throw new NoValueException();
        return applyFilter(list, parts[0].trim(), filterMethod, parts[1].trim());
    }

    private List<Ticket> applyFilter(List<Ticket> tickets, String field, String operator, String value) {
        List<Ticket> toRet = new ArrayList<>();
        for (Ticket ticket : tickets) {
            try {
                Object fieldValue;
                Field f;
                String[] fieldParts = field.split("\\.");
                if (fieldParts.length > 1) {
                    Object[] values = getNestedFieldValueForFilter(ticket, fieldParts);
                    if (values == null) continue;
                    fieldValue = values[0];
                    f = (Field) values[1];
                } else {
                    f = Ticket.class.getDeclaredField(field);
                    f.setAccessible(true);
                    fieldValue = f.get(ticket);
                }

                try {
                    switch (operator) {
                        case "contains":
                            if (fieldValue == null) continue;
                            String fieldValueStr = String.valueOf(fieldValue);
                            if (fieldValueStr.toLowerCase().contains(value.toLowerCase()))
                                toRet.add(ticket);
                            break;
                        case "=":
                            if (fieldValue == null) continue;
                            if (((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) == 0)
                                toRet.add(ticket);
                            break;
                        case "!=":
                            if (fieldValue == null) {
                                toRet.add(ticket);
                                continue;
                            }
                            ;
                            if (((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) != 0)
                                toRet.add(ticket);
                            break;
                        case ">":
                            if (fieldValue == null) continue;
                            if (((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) > 0)
                                toRet.add(ticket);
                            break;
                        case "<":
                            if (fieldValue == null) {
                                toRet.add(ticket);
                                continue;
                            }
                            ;
                            if (((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) < 0)
                                toRet.add(ticket);
                            break;
                        case ">=":
                            if (fieldValue == null) continue;
                            if (((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) >= 0)
                                toRet.add(ticket);
                            break;
                        case "<=":
                            if (fieldValue == null) {
                                toRet.add(ticket);
                                continue;
                            }
                            ;
                            if (((Comparable) fieldValue).compareTo(parseValue(value, f.getType())) <= 0)
                                toRet.add(ticket);
                            break;
                        default:
                            throw new NoFilterMethodException(operator);
                    }

                } catch (NumberFormatException e) {
                    TypeMismatchException q = new TypeMismatchException(value, f.getType());
                    q.initPropertyName(f.getName());
                    throw q;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new NoFieldException(field);
            }
        }

        return toRet;
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
            try {
                return targetType.getMethod("fromValue", String.class).invoke(null, value.toUpperCase());
            } catch (InvocationTargetException e) {
                TypeMismatchException q = new TypeMismatchException(value, TicketType.class);
                q.initPropertyName(targetType.getSimpleName());
                throw q;
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
        } else {
            return value;
        }
    }

    private Object[] getNestedFieldValueForFilter(Ticket ticket, String[] fieldParts) throws NoSuchFieldException, IllegalAccessException {
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

    private void updateNonObjectFields(Ticket oldTicket, TicketWriteUpdate newTicket) {
        if (newTicket.getName() != null) oldTicket.setName(newTicket.getName());
        if (newTicket.getPrice() != null) oldTicket.setPrice(newTicket.getPrice());
        if (newTicket.getDiscount() != null) oldTicket.setDiscount(newTicket.getDiscount());
        if (newTicket.getRefundable() != null) oldTicket.setRefundable(newTicket.getRefundable());
        if (newTicket.getType() != null) oldTicket.setType(TicketType.valueOf(newTicket.getType()));
    }

    private void updateCoordinates(Ticket oldTicket, TicketWriteUpdate newTicket) {
        if (newTicket.getCoordinates() != null) {
            if (newTicket.getCoordinates().getX() != null)
                oldTicket.getCoordinates().setX(newTicket.getCoordinates().getX());
            if (newTicket.getCoordinates().getY() != null)
                oldTicket.getCoordinates().setY(newTicket.getCoordinates().getY());
        }
    }

    private void updatePersonAndLocationIfExists(Ticket oldTicket, TicketWriteUpdate newTicket) {
        if (newTicket.getPerson().getHeight() != null)
            oldTicket.getPerson().setHeight(newTicket.getPerson().getHeight());
        if (newTicket.getPerson().getEyeColor() != null)
            oldTicket.getPerson().setEyeColor(EyeColor.valueOf(newTicket.getPerson().getEyeColor().toUpperCase()));
        if (newTicket.getPerson().getHairColor() != null)
            oldTicket.getPerson().setHairColor(HairColor.valueOf(newTicket.getPerson().getHairColor().toUpperCase()));
        if (newTicket.getPerson().getNationality() != null)
            oldTicket.getPerson().setNationality(Country.valueOf(newTicket.getPerson().getNationality().toUpperCase()));
        if (newTicket.getPerson().getLocation() != null) {
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

    private void updatePersonIfNotExists(Ticket oldTicket, TicketWriteUpdate newTicket) {
        if (newTicket.getPerson().getHeight() != null &&
                newTicket.getPerson().getHairColor() != null &&
                (newTicket.getPerson().getLocation() != null &&
                        newTicket.getPerson().getLocation().getX() != null &&
                        newTicket.getPerson().getLocation().getZ() != null)) {
            oldTicket.setPerson(new Person());
            oldTicket.getPerson().setHeight(newTicket.getPerson().getHeight());
            oldTicket.getPerson().setHairColor(HairColor.valueOf(newTicket.getPerson().getHairColor().toUpperCase()));
            if (newTicket.getPerson().getEyeColor() != null)
                oldTicket.getPerson().setEyeColor(EyeColor.valueOf(newTicket.getPerson().getEyeColor().toUpperCase()));
            if (newTicket.getPerson().getNationality() != null)
                oldTicket.getPerson().setNationality(Country.valueOf(newTicket.getPerson().getNationality().toUpperCase()));

            oldTicket.getPerson().setLocation(new Location());
            oldTicket.getPerson().getLocation().setX(newTicket.getPerson().getLocation().getX());
            oldTicket.getPerson().getLocation().setZ(newTicket.getPerson().getLocation().getZ());

            if (newTicket.getPerson().getLocation().getY() != null)
                oldTicket.getPerson().getLocation().setY(newTicket.getPerson().getLocation().getY());
            if (newTicket.getPerson().getLocation().getName() != null)
                oldTicket.getPerson().getLocation().setName(newTicket.getPerson().getLocation().getName());
        } else {
            List<String> errors = checkNecessaryFieldsInPerson(newTicket);
            throw new BadPersonException(errors);
        }

    }

    private static List<String> checkNecessaryFieldsInPerson(TicketWriteUpdate newTicket) {
        List<String> errors = new ArrayList<>();
        String message = ": Обязательно для заполнения";
        if (newTicket.getPerson().getHeight() == null) errors.add("person.height" + message);
        if (newTicket.getPerson().getHairColor() == null) errors.add("person.hairColor" + message);
        if (newTicket.getPerson().getLocation() == null) errors.add("person.location" + message);
        else {
            if (newTicket.getPerson().getLocation().getX() == null) errors.add("person.location.x" + message);
            if (newTicket.getPerson().getLocation().getZ() == null) errors.add("person.location.z" + message);
        }
        return errors;
    }

    private void updatePerson(Ticket oldTicket, TicketWriteUpdate newTicket) {
        if (newTicket.getPerson() != null) {
            if (oldTicket.getPerson() == null) {
                updatePersonIfNotExists(oldTicket, newTicket);
            } else {
                updatePersonAndLocationIfExists(oldTicket, newTicket);
            }
        }
    }

    private void isIntegerPositive(Integer num, String filedName) {
        if (num < 0) {
            throw new InvalidParameterException("Значение " + filedName + " должно быть больше нуля");
        }
    }
}
