package com.example.ticketServicePayara.converter;

import com.example.ticketServicePayara.dto.PersonWrite;
import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import com.example.ticketServicePayara.model.Person;

public class PersonWriteConverter {

    public static Person toPerson(PersonWrite personWrite){
        Person person = new Person();
        if(personWrite.getEyeColor() != null) person.setEyeColor(EyeColor.valueOf(personWrite.getEyeColor().toUpperCase()));
        person.setHeight(personWrite.getHeight());
        if(personWrite.getNationality() != null) person.setNationality(Country.valueOf(personWrite.getNationality().toUpperCase()));
        person.setHairColor(HairColor.valueOf(personWrite.getHairColor().toUpperCase()));
        person.setLocation(LocationWriteConverter.toLocation(personWrite.getLocation()));
        return person;
    }


    public static PersonWrite toPersonWrite(Person person){
        PersonWrite personWrite = new PersonWrite();
        personWrite.setId(person.getId());
        if(person.getEyeColor() != null) personWrite.setEyeColor(person.getEyeColor().name().toUpperCase());
        personWrite.setHeight(person.getHeight());
        if(person.getNationality() != null) personWrite.setNationality(person.getNationality().name().toUpperCase());
        personWrite.setHairColor(person.getHairColor().name().toUpperCase());
        personWrite.setLocation(LocationWriteConverter.toLocationWrite(person.getLocation()));
        return personWrite;
    }
}
