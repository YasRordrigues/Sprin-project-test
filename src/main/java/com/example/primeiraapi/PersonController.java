package com.example.primeiraapi;

import com.example.primeiraapi.data.vo.v1.PersonVO;
import com.example.primeiraapi.data.vo.v2.PersonVOV2;
import com.example.primeiraapi.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/person")
public class PersonController {
  @Autowired
  private PersonServices service;


  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO findById(
            @PathVariable(value = "id") Long id) {
    return service.findById(id);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PersonVO> findAll() {
    return service.findAll();
  }

  @PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO createPerson(
           @RequestBody PersonVO person){
    return service.createPerson(person);
  }

  @PostMapping(value = "/v2", consumes= MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVOV2 createV2Person(
          @RequestBody PersonVOV2 person){
    return service.createV2Person(person);
  }

  @PutMapping(consumes= MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public PersonVO updatePerson(
           @RequestBody PersonVO person){

    return service.updatePerson(person);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deletePerson(
          @PathVariable(value = "id") Long id) {
          service.deletePerson(id);
          return ResponseEntity.noContent().build();
  }

}
