package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Driverlocation;

public interface DriverLocationRepository extends CrudRepository<Driverlocation, Long> {

	List<Driverlocation> findAll();
}