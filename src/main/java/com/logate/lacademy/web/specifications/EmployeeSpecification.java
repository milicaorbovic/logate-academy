package com.logate.lacademy.web.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.domain.Specification;

import com.logate.lacademy.domains.Employee;
import com.logate.lacademy.domains.User;
import com.logate.lacademy.web.search.EmployeeSearch;

@Description(value = "Employee Specification.")
public class EmployeeSpecification implements Specification<Employee> {
	
	private EmployeeSearch employeeSearch;
	
	public EmployeeSpecification(EmployeeSearch employeeSearch) {
		this.employeeSearch = employeeSearch;
	}

	@Override
	public Predicate toPredicate(Root<Employee> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) 
	{
		final List<Predicate> predicates = new ArrayList<>();
		Join<Employee, User> user = root.join("user");
		
		Predicate orClause;
		Predicate eqPred;
		
		// first name && last name
		if (employeeSearch.getFirstName() != null && employeeSearch.getLastName() != null)
		{
			orClause = criteriaBuilder.or(
				criteriaBuilder.like(user.get("firstName"), employeeSearch.getFirstName()),
				criteriaBuilder.like(user.get("lastName"), employeeSearch.getLastName())	
			);
			
			predicates.add(orClause);
		}
		
		// job description...
		if (employeeSearch.getJobDescription() != null)
		{
			eqPred = criteriaBuilder.equal(root.get("jobDescription"), employeeSearch.getJobDescription());
			predicates.add(eqPred);
		}
		
		
		// first name
//		if (employeeSearch.getFirstName() != null) {
//			predicates.add(criteriaBuilder.equal(user.get("firstName"), employeeSearch.getFirstName()));
//		}
//		
//		// last name
//		if (employeeSearch.getLastName() != null) {
//			predicates.add(criteriaBuilder.equal(user.get("lastName"), employeeSearch.getLastName()));
//		}
//		
//		// job description
//		if (employeeSearch.getJobDescription() != null) {
//			predicates.add(criteriaBuilder.equal(root.get("jobDescription"), employeeSearch.getJobDescription()));
//		}
		
		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}
}
