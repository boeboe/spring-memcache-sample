package be.example.backend.service.jpa;

import be.example.backend.domain.Person;
import be.example.backend.repository.PersonRepository;
import be.example.backend.service.PersonService;
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
        simulateSlowService();
        List<Person> personList = personRepository.findAll();
        return personList;
    }

    @Override
    public Person findById(Long id) {
        simulateSlowService();
        Person person = personRepository.findOne(id);
        LOGGER.info("Found person {} with id {}", person, id);
        return person;
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 2000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
