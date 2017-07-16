package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Requests;

public interface RequestsRepository extends CrudRepository<Requests, Long> {

	List<Requests> findRequestsByCustomerId(long id);

}
