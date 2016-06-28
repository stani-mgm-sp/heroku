package de.mgm.sp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cache.element")
public class CacheElementConfig {

	private Integer timeToIdleSeconds;
	private Integer timeToLiveSeconds;

	public Integer getTimeToIdleSeconds() {
		return timeToIdleSeconds;
	}

	public void setTimeToIdleSeconds(Integer timeToIdleSeconds) {
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	public Integer getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(Integer timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

}
