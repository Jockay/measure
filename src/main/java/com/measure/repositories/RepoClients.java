package com.measure.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.measure.entities.Client;

public interface RepoClients extends JpaRepository<Client, Integer> {

	List<Client> findByClientIp(String clientIp);
	
}
