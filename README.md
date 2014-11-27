Spring Memcache Sample
======================

A small working out of the box sample to demo the use of Spring's `@Cache` annotations on a Spring MVC powered REST API.


## Used libraries ##

The following is a list of most important libraries used in the demo:

library | version | comment
----|------|----
 **springframework** | 4.1.2.RELEASE | Spring Core
 **spring-data-jpa** | 1.7.1.RELEASE | Persistency FW
 **spring-hateoas** | 0.16.0.RELEASE | REST HATEOAS support
 **hibernate** | 4.3.7.Final | ORM
 **jackson** | 2.4.4 | JSON support
 **h2** | 1.4.182 | Embedded in memory DB
 **simple-spring-memcached** | 3.5.1-SNAPSHOT | Annotational Memcached Caching
 **ehcache** | 2.9.0 | In JVM cache
 **jetty-runner** | 9.2.5.v20141112 | Jetty webrunner
 **webapp-runner** | 7.0.40.1 | Tomcat webrunner


##Caching backends##

The sample shows the use of 3 different caching backends:

 * **EHCache** an in JVM caching solution
 * **SpyMemcached** single threaded **memcached** based solution
 * **XMemcached** multi threaded **memcahced** based solution

A three caching backend are configured using the Spring Java based `@Configuration` method. Since only one backend is supported at a time, you will need to comment out the others to switch between backends (*spymemcached* is defaulted).

Make sure you have memcached installed on your system before experimenting with the memcached based solutions:

    sudo apt-get install memcached

The memcached server address is configured using a property file `cache.properties`:

    cache.server=127.0.0.1:11211


##The sample##

The demo sample provides two REST endpoints, to demonstrate the difference with or without caching:

 * http://localhost:9090/rest/persons (none cached list of person)
 * http://localhost:9090/rest/persons/3 (none cached person)
 * http://localhost:9090/rest/cache-persons (cached list of person)
 * http://localhost:9090/rest/cache-persons/3 (cached person)

Caching is applied on the service methods used by the REST controllers, which are delayed (`sleep 2000`) in purpose the accentuate the difference. Debugging is enabled in `log4j.xml` to show cache hits/misses:

	INFO  SSMCache - Cache miss. Get by key 3 from cache defaultCache
	Hibernate: select cacheperso0_.id as id1_0_0_, cacheperso0_.first_name as first_na2_0_0_, cacheperso0_.last_name as last_nam3_0_0_, cacheperso0_.version as version4_0_0_ from person cacheperso0_ where cacheperso0_.id=?
	INFO  CachePersonServiceImpl - Found cachePerson CachePerson{id=3, firstName='Donald', lastName='Duck', version=0} with id 3
	INFO  SSMCache - Put 'CachePerson{id=3, firstName='Donald', lastName='Duck', version=0}' under key 3 to cache defaultCache
	
	INFO  SSMCache - Cache hit. Get by key 3 from cache defaultCache value 'CachePerson{id=3, firstName='Donald', lastName='Duck', version=0}'

Corresponding JSON responses:

	{
	  "firstName" : "Donald",
	  "lastName" : "Duck",
	  "fetchTime" : 2009,
	  "version" : 0,
	  "cachePersonId" : 3,
	  "links" : [ {
		"rel" : "self",
		"href" : "http://localhost:9090/rest/cache-persons/3"
	  } ],
	  "id" : 3
	}


	{
	  "firstName" : "Donald",
	  "lastName" : "Duck",
	  "fetchTime" : 1,
	  "version" : 0,
	  "cachePersonId" : 3,
	  "links" : [ {
		"rel" : "self",
		"href" : "http://localhost:9090/rest/cache-persons/3"
	  } ],
	  "id" : 3
	}


##Building and running the sample##

Build the sample using maven:

    mvn clean install
    
In the root of the project a `run.sh` script is provided which start the Spring based web archive (war) using Jetty or Tomcat webrunners, on port 9090. Edit the file to tweak this behavior or execute the java commands directly providing the proper arguments.

    java -jar target/dependency/jetty-runner.jar --port 9090 target/*.war
    java -jar target/dependency/webapp-runner.jar --port 9090 --expand-war target/*.war


##External References##

 * https://github.com/ragnor/simple-spring-memcached
 * https://github.com/couchbase/spymemcached
 * https://github.com/killme2008/xmemcached


Thanks a lot to @ragnor for his support on getting the memcached backends properly configured using @Configuration !
