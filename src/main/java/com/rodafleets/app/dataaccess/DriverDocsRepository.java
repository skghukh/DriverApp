package com.rodafleets.app.dataaccess;

import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.DriverDocs;

public interface DriverDocsRepository extends CrudRepository<DriverDocs, Long> {

}
