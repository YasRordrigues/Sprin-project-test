package com.example.primeiraapi.services;


import com.example.primeiraapi.exceptions.ResourcedNotFountException;
import com.example.primeiraapi.model.Person;
import com.example.primeiraapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll(){

        return repository.findAll();

    }
    public Person findById(Long id){

        return repository.findById(id)
                .orElseThrow(() -> new ResourcedNotFountException("no records for this ID! "));
    }

    public Person createPerson(Person person) {
        logger.info("Creating one person");
        return repository.save(person);
    }

    public Person updatePerson(Person person) {
        logger.info("Updating one person");
        var entity =  repository.findById(person.getId())
                .orElseThrow(() -> new ResourcedNotFountException("no records for this ID! "));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting one person");
        var entity =  repository.findById(id)
                .orElseThrow(() -> new ResourcedNotFountException("no records for this ID! "));
        repository.delete(entity);
    }

}
