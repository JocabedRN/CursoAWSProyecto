package com.proyectoaws.encuesta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoaws.encuesta.dao.PersonaRepository;
import com.proyectoaws.encuesta.dto.PersonaInput;
import com.proyectoaws.encuesta.model.Persona;
import com.proyectoaws.encuesta.service.IEncuestaService;

@Service
public class EncuestaService implements IEncuestaService{
	@Autowired
	private PersonaRepository personaRepository;
	
	@Override
	public List<Persona> obtenerPersonas(){
		return personaRepository.findAll();
	}
	@Override
	public void guardarPersona(PersonaInput personaInput) {
		Persona persona = new Persona();
		persona.setIdpersona(personaInput.getIdpersona());
		persona.setNombres(personaInput.getNombres());
		persona.setApellidos(personaInput.getApellidos());
		persona.setEdad(personaInput.getEdad());
		persona.setLenguaje(personaInput.getLenguaje());
		personaRepository.save(persona);
	}
}
