package be.example.backend.service;

import be.example.backend.domain.CachePerson;

import java.util.List;

/**
 * Created by vanbosb on 25/11/14.
 */
public interface CachePersonService {

    public List<CachePerson> findAll();

    public CachePerson findById(Long id);

}
