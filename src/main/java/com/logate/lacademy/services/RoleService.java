package com.logate.lacademy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logate.lacademy.domains.Role;
import com.logate.lacademy.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public Role store(Role role) {
		return roleRepository.save(role);
	}
}
