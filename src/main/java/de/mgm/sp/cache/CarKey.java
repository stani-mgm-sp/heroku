package de.mgm.sp.cache;

import de.mgm.sp.model.Car;

public class CarKey extends CacheKey {

	public CarKey(Car car) {
		super(car.getId());
	}

}
