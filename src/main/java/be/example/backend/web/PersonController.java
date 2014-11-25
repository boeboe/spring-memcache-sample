package be.example.backend.web;

import be.example.backend.domain.Person;
import be.example.backend.service.PersonService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by vanbosb on 25/11/14.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    public static Function<Person, Person> addLinks = new Function<Person, Person>() {
        @Override
        public Person apply(Person input) {
            addLinks(input);
            return input;
        }
    };

    @Autowired
    private PersonService personService;

    public static void addLinks(Person Person) {
        if (null != Person) {
            Person.add(linkTo(PersonController.class).slash(Person.getPersonId()).withSelfRel());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Person> getPersons() {
        List<Person> PersonList = personService.findAll();
        return Lists.transform(PersonList, addLinks);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Person getPersonById(@PathVariable Long id) {
        Person Person = personService.findById(id);
        addLinks(Person);
        return Person;
    }

}
