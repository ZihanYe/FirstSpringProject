package com.clover.FirstSpringProject.Controllers.Exceptions;

public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id + "\n");
  }
}
