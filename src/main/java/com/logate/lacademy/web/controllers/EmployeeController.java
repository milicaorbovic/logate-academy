package com.logate.lacademy.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.logate.lacademy.domains.Employee;
import com.logate.lacademy.services.EmployeeService;
import com.logate.lacademy.web.dto.EmployeeDTO;
import com.logate.lacademy.web.exceptions.ValidationException;
import com.logate.lacademy.web.search.EmployeeSearch;
import com.logate.lacademy.web.specifications.EmployeeSpecification;
import com.logate.lacademy.web.validators.EmployeeValidator;


@Description(value = "Employee Endpoint Controller.")
@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeValidator employeeValidator;

	
	@RequestMapping(value = "/employees", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmployeesPageable(Pageable pageable)
	{
		Page<Employee> employeePage = employeeService.findByPageable(pageable);
		return new ResponseEntity<>(employeePage.getContent(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees/auth", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("@authComponent.hasHeaderPermission(#authHeader)")
	public ResponseEntity<List<Employee>> getListAuthorized(@RequestHeader(value = "Authorization") String authHeader)
	{
		List<Employee> employees = new ArrayList<>();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createNew(@Valid @RequestBody Employee employee)
	{
		Employee dbEmployee = employeeService.store(employee);
		return new ResponseEntity<>(dbEmployee, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/employees/validator",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createSomething(@RequestBody EmployeeDTO employee)
		throws Exception
	{
		// povezivanje
		Errors potentialErrors = new BeanPropertyBindingResult(employee, "employee");
		ValidationUtils.invokeValidator(employeeValidator, employee, potentialErrors);
		
		// provjera gresaka
		if (potentialErrors.hasErrors()) {
			throw new ValidationException(potentialErrors);
		}
		
		LOGGER.info("Validation passed");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees/short",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeDTO>> getAllEmployeesShort()
	{
		List<EmployeeDTO> employees = employeeService.findEmployeesShort();
		return Optional.ofNullable(employees)
				.map(employeeList -> new ResponseEntity<>(employeeList, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@RequestMapping(value = "/employees/search", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllBySpec(@ModelAttribute EmployeeSearch employeeSearch)
	{
		LOGGER.info("Params: {}", employeeSearch);
		
		EmployeeSpecification employeeSpec = new EmployeeSpecification(employeeSearch);
		List<Employee> employees = employeeService.findAllBySpec(employeeSpec);
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees/headers", method = RequestMethod.GET)
	public ResponseEntity<Void> mapHeaders(@RequestHeader(value = "Authorization", required = false) String authHeader,
			@RequestHeader(value = "X-Header", required = false) String h)
	{
		LOGGER.info("Auth header: {}", authHeader);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees/headers/all", method = RequestMethod.GET)
	public ResponseEntity<Void> mapAllHeaders(@RequestHeader Map<String, Object> headersMap)
	{
		LOGGER.info("All request headers: {}", headersMap);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees/short/entity", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeDTO>> findAllByEntityManager()
	{
		List<EmployeeDTO> employees = employeeService.findDTO();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees/short/native", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeDTO>> findEmployeeListByNative()
	{
		List<EmployeeDTO> employees = employeeService.findByNativeQuery();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employees/short/native/ids", method = RequestMethod.GET)
	public ResponseEntity<List<Integer>> findEmployeeIdentifiers()
	{
		List<Integer> employees = employeeService.findNativeIdentifiers();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	
	/*
	 * SECURITY *
	 */
	 
	@RequestMapping(value = "/employees/authorized", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("@authComponent.hasHeaderPermission(#authHeader)")
	public ResponseEntity<List<EmployeeDTO>> findEmployeesAuthorized(@RequestHeader(value = "Authorization") String authHeader)
	{
		List<EmployeeDTO> employees = employeeService.findDTO();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
}
