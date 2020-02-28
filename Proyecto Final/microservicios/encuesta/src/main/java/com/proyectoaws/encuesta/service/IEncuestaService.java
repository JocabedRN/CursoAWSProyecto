package com.proyectoaws.encuesta.service;

import java.util.List;

import com.proyectoaws.encuesta.dto.PersonaInput;
import com.proyectoaws.encuesta.model.Persona;

public interface IEncuestaService {
	public List<Persona> obtenerPersonas();
	public void guardarPersona(PersonaInput persona);
}
