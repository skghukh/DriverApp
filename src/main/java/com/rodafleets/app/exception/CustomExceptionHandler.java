package com.rodafleets.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.rodafleets.app.config.AppConfig;
import com.rodafleets.app.response.CustomResponse;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<CustomResponse> exceptionHandler(RuntimeException e) throws RuntimeException {
		CustomResponse error = new CustomResponse();
		//default 
		error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("Internal server error");
		// error.setCode(e.getErrorCode());
		//System.out.println(e.getLocalizedMessage());
		//System.out.println(e.getCause().getLocalizedMessage());
		if (e.getCause() == null) {
			throw e; // not what you're interested in, throw it back
		}
		Throwable nested = e.getCause().getCause();
		if (nested instanceof MySQLIntegrityConstraintViolationException) {
			MySQLIntegrityConstraintViolationException constraintViolation = (MySQLIntegrityConstraintViolationException)nested;
			String message = constraintViolation.getMessage();
			String sqlState = constraintViolation.getSQLState();
			int errorCode = constraintViolation.getErrorCode();
			// create validation message or whatever
			error.setMessage(message);
			error.setCode(AppConfig.DUPLICATE_ENTRY);
		} else {
			throw e; // not what you're interested in, throw it back
		}
		
		return new ResponseEntity<CustomResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(MySQLIntegrityConstraintViolationException.class)
	public ResponseEntity<CustomResponse> runtimeExceptionHandler(MySQLIntegrityConstraintViolationException e) throws MySQLIntegrityConstraintViolationException {
		CustomResponse error = new CustomResponse();
		// error.setCode(e.getErrorCode());
		System.out.println(e.getLocalizedMessage());
		System.out.println(e.getCause());
		//		e.printStackTrace();
		error.setMessage("Please contact your administrator----sql errorcdsfdfdasf");
		return new ResponseEntity<CustomResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomResponse> exceptionHandler(Exception e) throws CustomException {
		CustomResponse error = new CustomResponse();
		error.setCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(e.getMessage());
		return new ResponseEntity<CustomResponse>(error, HttpStatus.BAD_REQUEST);
	}
}
