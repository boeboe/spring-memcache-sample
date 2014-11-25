package be.example.backend.repository;

import be.example.backend.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vanbosb on 25/11/14.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
