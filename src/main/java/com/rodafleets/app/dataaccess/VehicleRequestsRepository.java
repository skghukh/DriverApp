package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.VehicleRequest;
import com.rodafleets.app.model.VehicleRequestNotificationHistory;

public interface VehicleRequestsRepository extends CrudRepository<VehicleRequest, Long> {
	
	List<VehicleRequest> findRequestsByCustomerId(long id);
}
