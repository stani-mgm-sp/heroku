package de.mgm.sp.cache;

import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.config.Searchable;

public class CarCache implements CacheConfigurable {

	public static final String NAME = "car";

	public static final String SEARCH_ATTR_BRAND = "brand";
	public static final String SEARCH_ATTR_MODEL = "model";
	public static final String SEARCH_ATTR_COUNTRY = "country";

	@Override
	public String getCacheName() {
		return NAME;
	}

	@Override
	public Searchable getSearchable() {
		final Searchable searchable = new Searchable();
		searchable.setKeys(false);
		searchable.setValues(true);
		searchable.addSearchAttribute(new SearchAttribute().name(SEARCH_ATTR_BRAND));
		searchable.addSearchAttribute(new SearchAttribute().name(SEARCH_ATTR_MODEL));
		searchable.addSearchAttribute(new SearchAttribute().name(SEARCH_ATTR_COUNTRY));
		return searchable;
	}
}
