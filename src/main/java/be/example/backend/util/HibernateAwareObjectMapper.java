package be.example.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Class to support JSON serialization and de-serialization of Hibernate specific data types and properties, especially
 * lazy-loading aspects.
 * <p/>
 * Created by vanbosb on 03.11.14.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}
