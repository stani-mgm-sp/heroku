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

import de.mgm.sp.model.Car;
import de.mgm.sp.service.CarCacheManager;

@RestController
@RequestMapping(CarController.REQUEST_MAPPING)
@Api(value = CarController.REQUEST_MAPPING)
public class CarController {

	public static final String REQUEST_MAPPING = "/car";

	@Autowired
	private CarCacheManager carCacheComponent;

	@RequestMapping(value = "/put", method = POST)
	@ApiOperation(value = "Puts car to the cache.", notes = "Puts the given car to the cache.")
	@ResponseStatus(value = CREATED)
	public void put(//
			@RequestParam @ApiParam(value = "Defines the brand", required = true) final String brand, //
			@RequestParam @ApiParam(value = "Defines the model", required = true) final String model, //
			@RequestParam @ApiParam(value = "Defines the country", required = true) final String country//
	) {
		carCacheComponent.put(new Car(brand, model, country));
	}

	@RequestMapping(value = "/find", method = GET, produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets cars from the cache.", notes = "Gets the cars from the cache.")
	public List<Car> find(//
			@RequestParam(required = false) @ApiParam(value = "Defines the search value (key and/or value)", required = false) final String searchValue//
	) {
		return carCacheComponent.find(searchValue);
	}

	@RequestMapping(value = "/remove", method = DELETE)
	@ApiOperation(value = "Removes cars from the cache.", notes = "Removes cars from the cache.")
	public void remove(//
			@RequestParam(required = false) @ApiParam(value = "Defines the search value (key and/or value)", required = false) final String searchValue//
	) {
		carCacheComponent.remove(searchValue);
	}

}
