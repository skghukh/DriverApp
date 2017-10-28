package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.rodafleets.app.model.Trip;

public interface TripRepository extends CrudRepository<Trip, Long> {

	List<Trip> findAll();

	// query to select in progress trip for given driver
	@Query("SELECT tp FROM Trip tp WHERE tp.driverId= ? and tp.status<10 ORDER BY tp.id")
	Trip getTripInProgress(int driverId);

	@Transactional
	@Modifying
	@Query("Update Trip tp set tp.status = ? where tp.requestId = ?")
	void updateTrip(int status, long requestId);


}
