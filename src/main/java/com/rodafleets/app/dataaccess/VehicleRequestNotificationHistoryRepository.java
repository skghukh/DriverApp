package com.rodafleets.app.dataaccess;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Driver;
import com.rodafleets.app.model.VehicleRequestNotificationHistory;

public interface VehicleRequestNotificationHistoryRepository extends CrudRepository<VehicleRequestNotificationHistory, Long> {
	
	VehicleRequestNotificationHistory findOneByVehicleRequestIdAndDriverId(long requestId, long driverId);
	
	@Query("SELECT h FROM VehicleRequestNotificationHistory h JOIN h.vehicleRequest r WHERE h.driverId = ?1 AND h.status=0 AND r.expiration > CURRENT_DATE")
	ArrayList<VehicleRequestNotificationHistory> findActiveVehicleRequestsByDriverId(long driverId);
}
