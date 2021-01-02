package com.clover.FirstSpringProject.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.clover.AccessingDataJpa.EmployeeRepository;
import com.clover.ModelAssembler.EmployeeModelAssembler;
import com.clover.Employee.*;
import com.clover.FirstSpringProject.Controllers.Exceptions.EmployeeNotFoundException;

@RestController
public class EmployeeController {
	private final EmployeeRepository repository;
	private final EmployeeModelAssembler assembler;

	EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}
	
	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> all() {
		List<EntityModel<Employee>> employees = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]
	
	@PostMapping("/employees")
	public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
	    EntityModel<Employee> employeeEntity = assembler.toModel(repository.save(newEmployee));
	    return ResponseEntity.created(employeeEntity.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(employeeEntity);
	}
	
	// Single item
	@GetMapping("/employees/{id}")
	public EntityModel<Employee> one(@PathVariable Long id) {
	    Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	    return assembler.toModel(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<?> upsertEmployeeById(@RequestBody Employee newEmployee, @PathVariable Long id) {

		Employee upsertedEmployee = repository.findById(id)
	      .map(employee -> {
	        employee.setName(newEmployee.getFirstName(), newEmployee.getLastName());
	        return repository.save(employee);
	      })
	      .orElseGet(() -> {
	        return repository.save(newEmployee);
	      });
		EntityModel<Employee> employeeEntity = assembler.toModel(upsertedEmployee);
		return ResponseEntity.created(employeeEntity.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(employeeEntity);
	}
	
	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(@PathVariable Long id) {
	    repository.deleteById(id);
	}
	  
}
