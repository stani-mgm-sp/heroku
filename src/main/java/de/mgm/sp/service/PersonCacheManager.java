package de.mgm.sp.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import de.mgm.sp.cache.PersonCache;
import de.mgm.sp.cache.PersonKey;
import de.mgm.sp.config.CacheElementConfig;
import de.mgm.sp.config.PersonConfig;
import de.mgm.sp.model.Person;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;

@Component
public class PersonCacheManager {

	@Autowired
	private BaseCacheManager cacheComponent;

	@Autowired
	private PersonConfig personConfig;

	@Autowired
	private CacheElementConfig cacheElementConfig;

	public void put(Person person) {
		Assert.notNull(person);
		final PersonKey key = new PersonKey(person);
		final Cache cache = cacheComponent.getPersonCache();
		cache.put(new Element(key, person, cacheElementConfig.getTimeToIdleSeconds(),
				cacheElementConfig.getTimeToLiveSeconds()));
	}

	public List<Person> find(String country, Integer age) {
		return findCachedOrFromDBPersons(country, age, getPersonDBSupplier());
	}

	public void remove(String country, Integer age) {
		final Cache cache = cacheComponent.getPersonCache();
		findCachedPersonResultStream(country, age).forEach((result) -> cache.remove(result.getKey()));
	}

	private Supplier<Stream<Person>> getPersonDBSupplier() {
		return new Supplier<Stream<Person>>() {
			@Override
			public Stream<Person> get() {
				// TODO get the persons from DB
				final Person dummyPerson = new Person(personConfig.getName(), personConfig.getAge(),
						personConfig.getCountry());
				return Stream.of(dummyPerson);
			}
		};
	}

	private List<Person> findCachedOrFromDBPersons(String country, Integer age,
			Supplier<Stream<Person>> personSupplier) {
		final List<Person> cachedPersonList = findCachedPersonResultStream(country, age)//
				.map(result -> (Person) result.getValue())//
				.sorted(Comparator.comparing(Person::getName))//
				.collect(Collectors.toList());
		return cachedPersonList.isEmpty()
				? personSupplier//
						.get()//
						.peek(person -> put(person))//
						.sorted(Comparator.comparing(Person::getName))//
						.collect(Collectors.toList())//
				: cachedPersonList;
	}

	private Stream<Result> findCachedPersonResultStream(String country, Integer age) {
		final Cache cache = cacheComponent.getPersonCache();
		final Query query = cache.createQuery()//
				.includeKeys() // allow access to Result#getKey
				.includeValues(); // allow access to Result#getValue
		if (country != null) {
			final Attribute<String> countryAttr = cache.getSearchAttribute(PersonCache.SEARCH_ATTR_COUNTRY);
			query.addCriteria(countryAttr.eq(country));
		}
		if (age != null) {
			final Attribute<Integer> ageAttr = cache.getSearchAttribute(PersonCache.SEARCH_ATTR_AGE);
			query.addCriteria(ageAttr.le(age.intValue() + 10));
			query.addCriteria(ageAttr.ge(age.intValue() - 10));
		}
		return query.execute().all().stream();
	}

}
