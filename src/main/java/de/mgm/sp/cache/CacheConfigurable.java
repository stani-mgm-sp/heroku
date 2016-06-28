package de.mgm.sp.cache;

import net.sf.ehcache.config.Searchable;

public interface CacheConfigurable {

	String getCacheName();

	default int getMaxEntriesLocalHeap() {
		return 1000;
	};

	default String getMaxBytesLocalHeap() {
		return null;
	};

	default int getTimeToLiveInSeconds() {
		return 0; // eternal
	};

	default int getTimeToIdleInSeconds() {
		return 0; // eternal
	};

	default Searchable getSearchable() {
		return null;
	};

}
