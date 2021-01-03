package com.clover.FirstSpringProject.Controllers.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.clover.FirstSpringProject.Controllers.Exceptions.EmployeeNotFoundException;

@ControllerAdvice
public class EmployeeNotFoundAdvice {
	@ResponseBody // signals that this advice is rendered straight into the response body
	@ExceptionHandler(EmployeeNotFoundException.class) // configure the advice to only respond if EmployeeNotFoundException is thrown
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(EmployeeNotFoundException ex) {
	    return ex.getMessage();
	}
}
