package com.example.primeiraapi.services;


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

@Service
public class PersonServices {
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll(){

        return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);

    }
    public PersonVO findById(Long id){

        var entity =  repository.findById(id)
                .orElseThrow(() -> new ResourcedNotFountException("no records for this ID! "));

        return DozerConverter.parseObject(entity, PersonVO.class);
    }

    public PersonVO createPerson(PersonVO person) {
        logger.info("Creating one person");
        var entity = DozerConverter.parseObject(person, Person.class);
        var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
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
        var entity =  repository.findById(person.getId())
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
