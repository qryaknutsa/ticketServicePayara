//package com.example.ticketServicePayara.service;
//
//
//import com.example.ticketServicePayara.dto.PersonDto;
//import com.example.ticketServicePayara.mappers.PersonMapper;
//import com.example.ticketServicePayara.dao.PersonDao;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.NotActiveException;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class PersonService {
//    private final PersonDao personDao;
//
//    public List<PersonDto> findAll() {
//        return personDao.getAll().stream().map(PersonMapper::toPersonDto).toList();
//    }
//
//    public PersonDto findById(int id) throws NotActiveException {
//        return PersonMapper.toPersonDto(personDao.getById(id));
//    }
//
//    public PersonDto save(PersonDto personDto) {
//        return PersonMapper.toPersonDto(personDao.save(PersonMapper.toPerson(personDto)));
//    }
//
//
//    public void deleteById(int id) {
//        personDao.deleteById(id);
//    }
//
//    public void delete(PersonDto personDto) {
//        personDao.delete(PersonMapper.toPerson(personDto));
//    }
//}
