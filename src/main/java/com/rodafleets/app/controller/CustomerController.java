package com.rodafleets.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodafleets.app.config.AppConfig;
import com.rodafleets.app.dataaccess.CustomerRepository;
import com.rodafleets.app.model.Customer;
import com.rodafleets.app.response.CustomResponse;


/*===================  Cutsomer API  =================== */

@RestController    // This means that this class is a Controller
@RequestMapping(path="/" + AppConfig.API_VERSION + "/customers") // This means URL's start with /customers after API base url
public class CustomerController {

	private static final Logger log = LoggerFactory.getLogger(DriverController.class);
	private CustomResponse jsonResponse;

	@Autowired // This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	private CustomerRepository customerRepo;

	/*
	 * Retrive all customers and their info
	 * @Url: localhost:8080/customers
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return findAllCustomers();
	}

	/*
	 * Search customers with specified firstname
	 * @RequestParam: firstname
	 * @Url: localhost:8080/customers?firstname=?
	 */
	@RequestMapping(method = RequestMethod.GET, params="firstname")
	public ResponseEntity<?> getDriverByFirtsName(
			@RequestParam(value="firstname") String firstName
			) {
		return findCustomersByFirstName(firstName);
	}

	/*
	 * Search customers with specified firstname and lastname
	 * @RequestParam: firstname
	 * @RequestParam: lastname
	 * @Url: localhost:8080/customers?firstname=?&lastname=?
	 */
	@RequestMapping(method = RequestMethod.GET, params="{firstname, lastname}")
	public ResponseEntity<?> getDriverByFirstAndLastName(
			@RequestParam(value="firstname") String firstName,
			@RequestParam(value="lastname") String lastName
			) {
		return findCustomersByFirstAndLastName(firstName, lastName);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveCustomer(
			@RequestParam(value = "phonenumber") String phoneNumber,
			@RequestParam(value = "firstname") String firstName, @RequestParam(value = "lastname") String lastName,
			@RequestParam(value = "gender") String gender,
			@RequestParam(value = "android_token", required = false) String androidToken,
			@RequestParam(value = "ios_token", required = false) String iosToken) {
		return addCustomer(phoneNumber, firstName, lastName, gender);
	}

	/*
	 * 
	 * @RequestParam: firstname
	 * @RequestParam: lastname
	 * @Url: localhost:8080/drivers/1?firstname=?&lastname=?
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> updateCustomer(
			@PathVariable("id") long id,
			@RequestParam(value="firstname", required=false) String firstName,
			@RequestParam(value="lastname", required=false) String lastName,
			@RequestParam(value="gender", required=false) String gender,
			@RequestParam(value="password", required=false) String password,
			@RequestParam(value="company_name", required=false) String companyName,
			@RequestParam(value="address", required=false) String address) {
		return editCustomer(id, firstName, lastName, gender, password, companyName, address);
	}




	private ResponseEntity<?> findAllCustomers() {
		jsonResponse = new CustomResponse();
		List<Customer> customers = customerRepo.findAll();
		//
		if(customers.isEmpty()) {
			jsonResponse.setCode(HttpStatus.NO_CONTENT.value());
			jsonResponse.setMessage("No customers added.");
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}	    
		return new ResponseEntity<List>(customers, HttpStatus.CREATED);
	}

	private ResponseEntity<?> findCustomersByFirstName(String firstName) {
		jsonResponse = new CustomResponse();
		List<Customer> customers = customerRepo.findByFirstNameAllIgnoreCase(firstName);
		if(customers.isEmpty()) {
			log.info("search returned empty");
			jsonResponse.setCode(HttpStatus.NO_CONTENT.value());
			jsonResponse.setMessage("No results for given search");
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}	    
		return new ResponseEntity<List>(customers, HttpStatus.CREATED);
	}

	private ResponseEntity<?> findCustomersByFirstAndLastName(String firstName, String LastName) {
		jsonResponse = new CustomResponse();
		List<Customer> customers = customerRepo.findByFirstNameAndLastNameAllIgnoreCase(firstName, firstName);
		if(customers.isEmpty()) {
			log.info("search returned empty");
			jsonResponse.setCode(HttpStatus.NO_CONTENT.value());
			jsonResponse.setMessage("No results for given search");
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}	    
		return new ResponseEntity<List>(customers, HttpStatus.CREATED);
	}
	
	private ResponseEntity<?> addCustomer(String phoneNumber, String firstName, String lastName, String gender) {
		jsonResponse = new CustomResponse();
		try {
			Customer customer = new Customer(phoneNumber);
			customer.setGender(gender); // default value
			customer.setPassword(""); // default value
			customer.setFirstName(firstName); // default value
			customer.setLastName(lastName); // default value
			customerRepo.save(customer);
		}
		catch (Exception ex) {
			jsonResponse.setCode(HttpStatus.PRECONDITION_FAILED.value());
			jsonResponse.setMessage(ex.getMessage());
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}

		jsonResponse.setMessage("Customer created!");	    
		return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.CREATED);
	}
	
	private ResponseEntity<?> editCustomer(long id, String firstName, String lastName, String gender, String password, String companyName, String address) {
		jsonResponse = new CustomResponse();
		try {
			Customer customer = customerRepo.findOne(id);
			if(firstName != null && !firstName.isEmpty()) {
				customer.setFirstName(firstName);
			}

			if(lastName != null && !lastName.isEmpty()) {
				customer.setLastName(lastName);
			}

			if(gender != null && !gender.isEmpty()) {
				customer.setGender(gender);
			}

			if(password != null && !password.isEmpty()) {
				customer.setPassword(password);
			}
			
			if(companyName != null && !companyName.isEmpty()) {
				customer.setPassword(companyName);
			}
			
			if(address != null && !address.isEmpty()) {
				customer.setPassword(address);
			}

			customerRepo.save(customer);
		}
		catch (Exception ex) {
			jsonResponse.setCode(HttpStatus.PRECONDITION_FAILED.value());
			jsonResponse.setMessage(ex.getMessage());
			return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.OK);
		}

		jsonResponse.setMessage("Customer info updated!");	    
		return new ResponseEntity<CustomResponse>(jsonResponse, HttpStatus.CREATED);
	}
}
