package com.rodafleets.app.dataaccess;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Bid;
import com.rodafleets.app.model.Trip;

public interface TripRepository extends CrudRepository<Trip, Long> {

	List<Trip> findAll();
}
