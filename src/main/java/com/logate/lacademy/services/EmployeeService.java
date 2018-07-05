package com.logate.lacademy.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.logate.lacademy.domains.Employee;
import com.logate.lacademy.repository.EmployeeRepository;
import com.logate.lacademy.web.dto.EmployeeDTO;
import com.logate.lacademy.web.specifications.EmployeeSpecification;

@Description(value = "Employee Service")
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	/**
	 * Method for getting page-able employees
	 * 
	 * @param pageable - provided page-able object
	 * @return page of employees
	 */
	public Page<Employee> findByPageable(Pageable pageable) {
		 return employeeRepository.findAll(pageable);
	}
	
	
	/**
	 * Method for getting List of short Employees
	 * 
	 * @return List of Employee DTO Objects
	 */
	public List<EmployeeDTO> findEmployeesShort() {
		return employeeRepository.findShort();
	}
	
	
	/**
	 * Method for getting employee list by specification
	 * 
	 * @param employeeSpec
	 * @return
	 */
	public List<Employee> findAllBySpec(EmployeeSpecification employeeSpec) {
		return employeeRepository.findAll(employeeSpec);
	}
	
	
	/**
	 * Method for getting Employee DTO objects with Entity Manager
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeeDTO> findDTO()
	{
		return entityManager.createNativeQuery("SELECT e.id as id, u.first_name as firstName, e.hire_date as hireDate "
				+ "from employees as e "
				+ "left join users as u on e.user_id = u.id")
			.unwrap(org.hibernate.query.Query.class)
			.setResultTransformer(Transformers.aliasToBean(EmployeeDTO.class))
			.getResultList();
	}
	
	
	/**
	 * Method for getting data by native query.
	 * 
	 * @return List of EmployeeDTO objects.
	 */
	public List<EmployeeDTO> findByNativeQuery()
	{
		List<Object[]> objectsList = employeeRepository.findByNative();
		List<EmployeeDTO> employees = new ArrayList<>();
		
		// processing data...
		objectsList
			.stream()
			.forEach(employee -> {
				EmployeeDTO employeeDTO = new EmployeeDTO(
					(Integer) employee[0],
					(String) employee[1],
					(Date) employee[2]
				);
				employees.add(employeeDTO);
			});
		
		return employees;
	}
	
	/**
	 * Method for getting employee identifiers by native queries.
	 * 
	 * @return List of identifiers (Integers)
	 */
	public List<Integer> findNativeIdentifiers() {
		return employeeRepository.findOnlyIds();
	}
	
	public Employee store(Employee employee) {
		return employeeRepository.save(employee);
	}
}
