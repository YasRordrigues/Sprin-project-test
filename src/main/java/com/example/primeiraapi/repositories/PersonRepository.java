package com.example.primeiraapi.repositories;

import com.example.primeiraapi.data.vo.v1.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}
