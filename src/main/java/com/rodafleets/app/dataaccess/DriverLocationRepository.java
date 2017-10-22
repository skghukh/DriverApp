package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Driverlocation;

public interface DriverLocationRepository extends CrudRepository<Driverlocation, Long> {

	List<Driverlocation> findAll();

	@Query("SELECT dl FROM Driverlocation dl WHERE dl.driverId = ?")
	Driverlocation getDriverLocationByDriverId(int driverId);

}