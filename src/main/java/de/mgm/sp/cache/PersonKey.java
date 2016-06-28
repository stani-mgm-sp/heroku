package de.mgm.sp.cache;

import de.mgm.sp.model.Person;

public class PersonKey extends CacheKey {

	/**
	 * The searchable attribute, don't rename. See
	 * {@link PersonCache#SEARCH_ATTR_COUNTRY}
	 */
	private String searchAttrCountry;

	/**
	 * The searchable attribute, don't rename. See
	 * {@link PersonCache#SEARCH_ATTR_AGE}
	 */
	private Integer searchAttrAge;

	public PersonKey(Person person) {
		super(person.getId());
		this.searchAttrCountry = person.getCountry();
		this.searchAttrAge = person.getAge();
	}

	public String getSearchAttrCountry() {
		return searchAttrCountry;
	}

	public Integer getSearchAttrAge() {
		return searchAttrAge;
	}
}
