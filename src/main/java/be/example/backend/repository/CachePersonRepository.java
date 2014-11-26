package be.example.backend.repository;

import be.example.backend.domain.CachePerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by vanbosb on 25/11/14.
 */
public interface CachePersonRepository extends JpaRepository<CachePerson, Long> {

    @Override
    List<CachePerson> findAll();

    @Override
    CachePerson getOne(Long id);
}
