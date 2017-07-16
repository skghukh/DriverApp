package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
	List<Vehicle> findAll();
	List<Vehicle> findByDriverId(long driverId);
	List<Vehicle> findByNumber(String vehicleNumber);
}
