package com.example.primeiraapi.services;


import com.example.primeiraapi.PersonController;
import com.example.primeiraapi.converter.DozerConverter;
import com.example.primeiraapi.converter.custom.PersonMapper;
import com.example.primeiraapi.data.vo.v1.PersonVO;
import com.example.primeiraapi.data.vo.v2.PersonVOV2;
import com.example.primeiraapi.exceptions.ResourcedNotFountException;
import com.example.primeiraapi.data.vo.v1.model.Person;
import com.example.primeiraapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll(){
        var persons =  DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
        persons
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()) );
        return  persons;
    }
    public PersonVO findById(Long id){

        var entity =  repository.findById(id)
                .orElseThrow(() -> new ResourcedNotFountException("no records for this ID! "));

        var vo =  DozerConverter.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO createPerson(PersonVO person) {
        logger.info("Creating one person");
        var entity = DozerConverter.parseObject(person, Person.class);
        var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV2 createV2Person(PersonVOV2 person) {
        logger.info("Creating one person with v2!");
        var entity = mapper.convertVoToEntity(person);
        var vo = mapper.convertEntityToVo(repository.save(entity));
        return vo;
    }

    public PersonVO updatePerson(PersonVO person) {
        logger.info("Updating one person");
        var entity =  repository.findById(person.getKey())
                .orElseThrow(() -> new ResourcedNotFountException("no records for this ID! "));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
        return vo;
    }

    public void deletePerson(Long id) {
        logger.info("Deleting one person");
        var entity =  repository.findById(id)
                .orElseThrow(() -> new ResourcedNotFountException("no records for this ID! "));
        repository.delete(entity);
    }

}
