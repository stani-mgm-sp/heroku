package de.mgm.sp.web;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import de.mgm.sp.model.CacheInfo;
import de.mgm.sp.service.BaseCacheManager;

@RestController
@RequestMapping(CacheController.REQUEST_MAPPING)
@Api(value = CacheController.REQUEST_MAPPING)
public class CacheController {

	public static final String REQUEST_MAPPING = "/cache";

	@Autowired
	private BaseCacheManager cacheComponent;

	@RequestMapping(value = "/createAll", method = POST)
	@ApiOperation(value = "Creates all caches.", notes = "Creates all available chaches.")
	@ResponseStatus(value = CREATED)
	public void createAll() {
		cacheComponent.createAll();
	}

	@RequestMapping(value = "/getAll", method = GET, produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets all caches.", notes = "Gets the complete cache list from cache manager.")
	public List<CacheInfo> getCacheList() {
		return cacheComponent.getCacheInfoList();
	}

	@RequestMapping(value = "/get/{cacheName}", method = GET, produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets the cache info.", notes = "Gets the cache by name from cache manager.")
	public CacheInfo getCacheInfo(
			@PathVariable @ApiParam(value = "Defines the cache", required = true) final String cacheName) {
		return cacheComponent.getCacheInfo(cacheName);
	}

	@RequestMapping(value = "/removeAll", method = DELETE)
	@ApiOperation(value = "Removes all caches.", notes = "Removes all available chaches.")
	public void removeAll() {
		cacheComponent.removeAll();
	}

	@RequestMapping(value = "/remove/{cacheName}", method = DELETE)
	@ApiOperation(value = "Removes the cache.", notes = "Removes the cache by name from cache manager.")
	public void remove(@PathVariable @ApiParam(value = "Defines the cache", required = true) final String cacheName) {
		cacheComponent.remove(cacheName);
	}

	@RequestMapping(value = "/removeCacheElements/{cacheName}", method = DELETE)
	@ApiOperation(value = "Removes the cache.", notes = "Removes the cache elements by name from cache manager.")
	public void removeCacheElements(
			@PathVariable @ApiParam(value = "Defines the cache", required = true) final String cacheName) {
		cacheComponent.removeCacheElements(cacheName);
	}

}
