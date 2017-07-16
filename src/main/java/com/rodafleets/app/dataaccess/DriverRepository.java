package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Driver;

public interface DriverRepository extends CrudRepository<Driver, Long> {

	List<Driver> findAll();
	List<Driver> findByFirstnameAllIgnoreCase(String firstName);
	List<Driver> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
	
	Driver findOneByPhoneNumberAndPassword(String phoneNumber, String password);
	Driver findOneByFirstname(String firstName);
	Driver findOneByPhoneNumber(String phoneNumber);
}
