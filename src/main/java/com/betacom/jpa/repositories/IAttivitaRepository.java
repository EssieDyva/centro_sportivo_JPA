package com.betacom.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.jpa.models.Attivita;

public interface IAttivitaRepository extends JpaRepository<Attivita, Integer>{
	Boolean existsByDescription(String desc);
	
}
