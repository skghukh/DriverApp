package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Vehicle;
import com.rodafleets.app.model.VehicleType;

public interface VehicleTypeRepository extends CrudRepository<VehicleType, Long>{
	List<VehicleType> findAll();
}
