package com.rodafleets.app.dataaccess;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findAll();
	List<Customer> findByFirstNameAllIgnoreCase(String firstName);
	List<Customer> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
	Customer findOneByPhoneNumberAndPassword(String phoneNumber, String password);
}
