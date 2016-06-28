package de.mgm.sp.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import de.mgm.sp.cache.CacheConfigurable;
import de.mgm.sp.cache.CarCache;
import de.mgm.sp.cache.PersonCache;
import de.mgm.sp.model.CacheInfo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Searchable;

@Component
public class BaseCacheManager {

	private static final CacheManager CACHE_MANAGER = CacheManager.getInstance();

	public Cache getCache(String cacheName) {
		return CACHE_MANAGER.getCache(cacheName);
	}

	public Cache getPersonCache() {
		return CACHE_MANAGER.getCache(PersonCache.NAME);
	}

	public Cache getCarCache() {
		return CACHE_MANAGER.getCache(CarCache.NAME);
	}

	public List<CacheInfo> getCacheInfoList() {
		return Arrays.stream(CACHE_MANAGER.getCacheNames())//
				.map(cacheName -> new CacheInfo(getCache(cacheName)))//
				.sorted(Comparator.comparing(CacheInfo::getName))//
				.collect(Collectors.toList());
	}

	public CacheInfo getCacheInfo(final String cacheName) {
		return new CacheInfo(getCache(cacheName));
	}

	public void remove(final String cacheName) {
		CACHE_MANAGER.removeCache(cacheName);
	}

	public void removeAll() {
		CACHE_MANAGER.removeAllCaches();
	}

	public void createAll() {
		addCache(new PersonCache());
		addCache(new CarCache());
	}

	public void removeCacheElements(String cacheName) {
		getCache(cacheName).removeAll();
	}

	private void addCache(CacheConfigurable appCache) {
		int maxEntriesLocalHeap = appCache.getMaxEntriesLocalHeap();
		String maxBytesLocalHeap = appCache.getMaxBytesLocalHeap();
		Searchable searchable = appCache.getSearchable();
		CacheConfiguration config = new CacheConfiguration(appCache.getCacheName(), maxEntriesLocalHeap);
		config.setTimeToLiveSeconds(appCache.getTimeToLiveInSeconds());
		config.setTimeToLiveSeconds(appCache.getTimeToIdleInSeconds());
		if (maxBytesLocalHeap != null) {
			config.setMaxBytesLocalHeap(maxBytesLocalHeap);
		}
		if (searchable != null) {
			config.addSearchable(searchable);
		}
		CACHE_MANAGER.addCache(new Cache(config));
	}

}
