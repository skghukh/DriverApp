package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.VehicleRequest;

public interface RequestsRepository extends CrudRepository<VehicleRequest, Long> {

	List<VehicleRequest> findRequestsByCustomerId(long id);

}
