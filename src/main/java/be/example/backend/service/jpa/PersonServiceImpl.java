package be.example.backend.service.jpa;

import be.example.backend.domain.Person;
import be.example.backend.repository.PersonRepository;
import be.example.backend.service.PersonService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vanbosb on 25/11/14.
 */
@Service("personService")
@Repository
@Transactional
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        return Lists.newArrayList(personRepository.findAll());
    }

    @Override
    public Person findById(Long id) {
        Person person = personRepository.findOne(id);
        LOGGER.info("Found person {} with id {}", person, id);
        return person;
    }

}
