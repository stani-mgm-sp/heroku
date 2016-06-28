package de.mgm.sp.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.mgm.sp.cache.CacheKey;
import de.mgm.sp.cache.CarCache;
import de.mgm.sp.cache.CarKey;
import de.mgm.sp.config.CacheElementConfig;
import de.mgm.sp.model.Car;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.expression.Criteria;

@Component
public class CarCacheManager {

	@Autowired
	private BaseCacheManager cacheComponent;

	@Autowired
	private CacheElementConfig cacheElementConfig;

	public void put(Car car) {
		final CacheKey key = new CarKey(car);
		final Cache cache = cacheComponent.getCache(CarCache.NAME);
		cache.put(new Element(key, car, cacheElementConfig.getTimeToIdleSeconds(),
				cacheElementConfig.getTimeToLiveSeconds()));
	}

	public List<Car> find(String searchValue) {
		return getResultList(searchValue).stream()//
				.map(result -> (Car) result.getValue())//
				.sorted(Comparator.comparing(Car::getBrand))//
				.collect(Collectors.toList());
	}

	public void remove(String searchValue) {
		final Cache cache = cacheComponent.getCache(CarCache.NAME);
		getResultList(searchValue).forEach((result) -> cache.remove(result.getKey()));
	}

	private List<Result> getResultList(String searchValue) {
		final Cache cache = cacheComponent.getCache(CarCache.NAME);
		final Query query = cache.createQuery()//
				.includeKeys()// allow access to Result#getKey
				.includeValues(); // allow access to Result#getValue
		if (searchValue != null) {
			final String searchValueRegex = String.format("*%s*", searchValue);

			final Attribute<String> brandAttr = cache.getSearchAttribute(CarCache.SEARCH_ATTR_BRAND);
			final Attribute<String> modelAttr = cache.getSearchAttribute(CarCache.SEARCH_ATTR_MODEL);
			final Attribute<String> countryAttr = cache.getSearchAttribute(CarCache.SEARCH_ATTR_COUNTRY);
			final Criteria brandCriteria = brandAttr.ilike(searchValueRegex);
			final Criteria modelCriteria = modelAttr.ilike(searchValueRegex);
			final Criteria countryCriteria = countryAttr.ilike(searchValueRegex);
			query.addCriteria(brandCriteria.or(modelCriteria).or(countryCriteria));
		}
		return query.execute().all();
	}
}
