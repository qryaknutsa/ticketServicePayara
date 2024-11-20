package com.example.ticketServicePayara.converter;

import com.example.ticketServicePayara.dto.PersonWrite;
import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import com.example.ticketServicePayara.model.Person;

public class PersonConverter {

    public static Person toPerson(PersonWrite personWrite){
        Person person = new Person();
        if(personWrite.getEyeColor() != null) person.setEyeColor(EyeColor.valueOf(personWrite.getEyeColor()));
        person.setHeight(personWrite.getHeight());
        if(personWrite.getNationality() != null) person.setNationality(Country.valueOf(personWrite.getNationality()));
        person.setHairColor(HairColor.valueOf(personWrite.getHairColor()));
        person.setLocation(LocationConverter.toLocation(personWrite.getLocation()));
        return person;
    }


    public static PersonWrite toPersonWrite(Person person){
        PersonWrite personWrite = new PersonWrite();
        if(person.getEyeColor() != null) personWrite.setEyeColor(person.getEyeColor().name());
        personWrite.setHeight(person.getHeight());
        if(person.getNationality() != null) personWrite.setNationality(person.getNationality().name());
        personWrite.setHairColor(person.getHairColor().name());
        personWrite.setLocation(LocationConverter.toLocationWrite(person.getLocation()));
        return personWrite;
    }
}
