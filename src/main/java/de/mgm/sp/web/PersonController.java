package de.mgm.sp.web;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import de.mgm.sp.model.Person;
import de.mgm.sp.service.PersonCacheManager;

@RestController
@RequestMapping(PersonController.REQUEST_MAPPING)
@Api(value = PersonController.REQUEST_MAPPING)
public class PersonController {

	public static final String REQUEST_MAPPING = "/person";

	@Autowired
	private PersonCacheManager personCacheComponent;

	@RequestMapping(value = "/put", method = POST)
	@ApiOperation(value = "Puts person to the cache.", notes = "Puts the given person to the cache.")
	@ResponseStatus(value = CREATED)
	public void put(//
			@RequestParam @ApiParam(value = "Defines the name", required = true) final String name, //
			@RequestParam @ApiParam(value = "Defines the age", required = true) final int age, //
			@RequestParam @ApiParam(value = "Defines the country", required = true) final String country//
	) {
		personCacheComponent.put(new Person(name, age, country));
	}

	@RequestMapping(value = "/find", method = GET, produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets persons from the cache.", notes = "Gets the persons from the cache.")
	public List<Person> find(//
			@RequestParam(required = false) @ApiParam(value = "Defines the country", required = false) final String country, //
			@RequestParam(required = false) @ApiParam(value = "Defines the age range (+/- 10)", required = false) final Integer age//
	) {
		return personCacheComponent.find(country, age);
	}

	@RequestMapping(value = "/remove", method = DELETE)
	@ApiOperation(value = "Removes persons from the cache.", notes = "Removes persons from the cache.")
	public void remove(//
			@RequestParam(required = false) @ApiParam(value = "Defines the country", required = false) final String country, //
			@RequestParam(required = false) @ApiParam(value = "Defines the age range (+/- 10)", required = false) final Integer age//
	) {
		personCacheComponent.remove(country, age);
	}

}
