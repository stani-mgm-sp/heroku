package de.mgm.sp.model;

import net.sf.ehcache.Cache;

public class CacheInfo {
	private final Cache cache;

	public CacheInfo(final Cache cache) {
		this.cache = cache;
	}

	public String getName() {
		return this.cache.getName();
	}

	public String getStatus() {
		return this.cache.getStatus().toString();
	}

	public boolean isDisabled() {
		return this.cache.isDisabled();
	}

	public int getCount() {
		return this.cache.getKeysWithExpiryCheck().size();
	}
}
