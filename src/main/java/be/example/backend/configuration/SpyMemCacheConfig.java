package be.example.backend.configuration;

import com.google.code.ssm.Cache;
import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.spymemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.providers.spymemcached.SpymemcachedConfiguration;
import com.google.code.ssm.spring.SSMCache;
import com.google.code.ssm.spring.SSMCacheManager;

import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.security.auth.callback.CallbackHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vanbosb on 22/11/14.
 * This configuration set up SSM artifacts/beans to support SSM cache annotations or use SSM as Spring Cache back-end.
 * <p/>
 * For more example code see:
 * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/cache/annotation/EnableCaching.html
 */

@PropertySource("classpath:cache.properties")
//@Configuration
@EnableCaching
@EnableAspectJAutoProxy
@ImportResource("classpath:simplesm-context.xml")
public class SpyMemCacheConfig extends CachingConfigurerSupport {

    private static final String USERNAME = System.getenv("MEMCACHIER_USERNAME");
    private static final String PASSWORD = System.getenv("MEMCACHIER_PASSWORD");
    private static final String SERVER = System.getenv("MEMCACHIER_SERVERS");

    @Autowired
    private Environment environment;

    @Bean
    @Override
    public CacheManager cacheManager() {
        Set<SSMCache> ssmCacheSet = new HashSet<>();
        SSMCache ssmCache = new SSMCache(defaultCache(), 300, false);

        ssmCacheSet.add(ssmCache);

        SSMCacheManager ssmCacheManager = new SSMCacheManager();
        ssmCacheManager.setCaches(ssmCacheSet);
        return ssmCacheManager;
    }

    @Bean
    @DependsOn("cacheBase")
    public CacheFactory cacheFactory() {
        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName("defaultCache");
        cacheFactory.setCacheClientFactory(new MemcacheClientFactoryImpl());

        String server = SERVER;
        if (StringUtils.isEmpty(server)) {
            server = environment.getProperty("cache.server");
        }

        SpymemcachedConfiguration cacheConfiguration = createCacheConfiguration(server);
        cacheFactory.setAddressProvider(new DefaultAddressProvider(server));
        cacheFactory.setConfiguration(cacheConfiguration);

        return cacheFactory;
    }

    @Bean
    public Cache defaultCache() {
        try {
            return cacheFactory().getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SpymemcachedConfiguration createCacheConfiguration(final String server) {
        SpymemcachedConfiguration cacheConfiguration = new SpymemcachedConfiguration();
        cacheConfiguration.setConsistentHashing(true);
        cacheConfiguration.setUseBinaryProtocol(true);

        // Authentication only applicable on Heroku cloud platform
        if (!StringUtils.isEmpty(USERNAME) && !StringUtils.isEmpty(PASSWORD)) {
            CallbackHandler callbackHandler = new PlainCallbackHandler(USERNAME, PASSWORD);
            AuthDescriptor authDescriptor = new AuthDescriptor(new String[]{"PLAIN"}, callbackHandler);
            cacheConfiguration.setAuthDescriptor(authDescriptor);
        }
        
        return cacheConfiguration;
    }

}
