package be.example.backend.service;

import be.example.backend.domain.Person;

import java.util.List;

/**
 * Created by vanbosb on 25/11/14.
 */
public interface PersonService {

    public List<Person> findAll();

    public Person findById(Long id);

}
