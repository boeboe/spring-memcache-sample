//package be.example.backend.configuration;
//
//import net.sf.ehcache.config.CacheConfiguration;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.cache.interceptor.SimpleKeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by vanbosb on 26/11/14.
// */
//@Configuration
//@EnableCaching
//public class EHCacheConfig extends CachingConfigurerSupport {
//
//    @Bean(destroyMethod = "shutdown")
//    public net.sf.ehcache.CacheManager ehCacheManager() {
//        CacheConfiguration cacheConfiguration = new CacheConfiguration();
//        cacheConfiguration.setName("defaultCache");
//        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
//        cacheConfiguration.setMaxEntriesLocalHeap(1000);
//
//        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
//        config.addCache(cacheConfiguration);
//
//        return net.sf.ehcache.CacheManager.newInstance(config);
//    }
//
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(ehCacheManager());
//    }
//
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new SimpleKeyGenerator();
//    }
//
//}
