package com.proyectoaws.encuesta.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectoaws.encuesta.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Integer> {

}
