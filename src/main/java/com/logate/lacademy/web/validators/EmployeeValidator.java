package com.logate.lacademy.web.validators;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.logate.lacademy.domains.Employee;
import com.logate.lacademy.repository.EmployeeRepository;
import com.logate.lacademy.web.dto.EmployeeDTO;

@Description(value = "Employee Validator.")
@Component
public class EmployeeValidator implements Validator {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(EmployeeDTO.class);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		EmployeeDTO employee = (EmployeeDTO) target;
		
		validateId(employee.getId(), errors);
		validateFirstName(employee.getFirstName(), errors);
	}
	
	private void validateId(Integer id, Errors errors)
	{
		if (id == null)
		{
			errors.rejectValue("id", "id.required", "Field `id` is required.");
			return;
		}
		
		Optional<Employee> employee = employeeRepository.findById(id);
		if (!employee.isPresent()) {
			errors.rejectValue("id", "id.not_exists", "Field `id` not exists.");
		}
	}
	
	private void validateFirstName(String firstName, Errors errors)
	{
		if (firstName == null)
		{
			errors.rejectValue("firstName", 
					"firstName.required", 
					"Field `firstName` is required.");
			return;
		}
		
		if (firstName.trim().contentEquals(""))
		{
			errors.rejectValue("firstName", 
					"firstName.empty", 
					"Field `firstName` is empty.");
		}
	}
}
