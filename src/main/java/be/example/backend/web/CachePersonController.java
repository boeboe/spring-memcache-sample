package be.example.backend.web;

import be.example.backend.domain.CachePerson;
import be.example.backend.service.CachePersonService;
import com.google.common.base.Function;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by vanbosb on 25/11/14.
 */
@RestController
@RequestMapping("/cache-persons")
public class CachePersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachePersonController.class);

    public static Function<CachePerson, CachePerson> addLinks = new Function<CachePerson, CachePerson>() {
        @Override
        public CachePerson apply(CachePerson input) {
            addLinks(input);
            return input;
        }
    };

    @Autowired
    private CachePersonService cachePersonService;

    public static void addLinks(CachePerson cachePerson) {
        if (null != cachePerson) {
            cachePerson.add(linkTo(CachePersonController.class).slash(cachePerson.getCachePersonId()).withSelfRel());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CachePerson> getCachePersons() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<CachePerson> cachePersonList = cachePersonService.findAll();
        stopwatch.stop();
        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        for (CachePerson cachePerson : cachePersonList) {
            cachePerson.setFetchTime(millis);
        }

        return Lists.transform(cachePersonList, addLinks);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CachePerson getCachePersonById(@PathVariable Long id) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        CachePerson cachePerson = cachePersonService.findById(id);
        stopwatch.stop();
        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        cachePerson.setFetchTime(millis);

        addLinks(cachePerson);
        return cachePerson;
    }

}
