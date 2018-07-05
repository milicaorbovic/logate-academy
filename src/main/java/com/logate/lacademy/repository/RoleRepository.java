package com.logate.lacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.logate.lacademy.domains.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	@Query(value = "select"
			+ " from roles"
			+ " where name = 'ROLE_USER'", nativeQuery = true)
	Role findRoleByName();

}
