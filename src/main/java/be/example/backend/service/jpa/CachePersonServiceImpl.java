package be.example.backend.service.jpa;

import be.example.backend.domain.CachePerson;
import be.example.backend.repository.CachePersonRepository;
import be.example.backend.service.CachePersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vanbosb on 25/11/14.
 */
@Service("cachePersonService")
@Repository
@Transactional
public class CachePersonServiceImpl implements CachePersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachePersonServiceImpl.class);

    @Autowired
    private CachePersonRepository cachePersonRepository;

    @Override
    @Cacheable(value = "defaultCache")
    public List<CachePerson> findAll() {
        simulateSlowService();
        List<CachePerson> cachePersonList = cachePersonRepository.findAll();
        return cachePersonList;
    }

    @Override
    @Cacheable(value = "defaultCache")
    public CachePerson findById(Long id) {
        simulateSlowService();
        CachePerson cachePerson = cachePersonRepository.findOne(id);
        LOGGER.info("Found cachePerson {} with id {}", cachePerson, id);
        return cachePerson;
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
