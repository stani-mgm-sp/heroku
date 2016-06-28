package de.mgm.sp.cache;

import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.config.Searchable;

public class PersonCache implements CacheConfigurable {

	public static final String NAME = "person";

	public static final String SEARCH_ATTR_COUNTRY = "searchAttrCountry";

	public static final String SEARCH_ATTR_AGE = "searchAttrAge";

	@Override
	public String getCacheName() {
		return NAME;
	}

	@Override
	public Searchable getSearchable() {
		final Searchable searchable = new Searchable();
		searchable.setKeys(true);
		searchable.setValues(false);
		searchable.addSearchAttribute(new SearchAttribute().name(SEARCH_ATTR_COUNTRY));
		searchable.addSearchAttribute(new SearchAttribute().name(SEARCH_ATTR_AGE));
		return searchable;
	}

}
