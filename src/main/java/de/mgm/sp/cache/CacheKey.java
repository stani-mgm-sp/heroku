package de.mgm.sp.cache;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The default cache key.
 */
public abstract class CacheKey implements Serializable {

	private String id;

	public CacheKey() {
		super();
	}

	public CacheKey(final String id) {
		this();
		this.id = id;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public String getId() {
		return id;
	}

}
