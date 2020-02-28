package com.proyectoaws.encuesta.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoaws.encuesta.dto.PersonaInput;
import com.proyectoaws.encuesta.dto.Respuesta;
import com.proyectoaws.encuesta.model.Persona;
import com.proyectoaws.encuesta.service.IEncuestaService;

@RestController
@CrossOrigin
@RequestMapping("api/encuesta")
public class EncuestaController {
	private static final Logger LOG = LoggerFactory.getLogger(EncuestaController.class);
	
	@Autowired
	private IEncuestaService encuestaService;
	

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value="obtenerpersonas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Persona>> obtenerTodos(Pageable pageable){
		try {
			return new ResponseEntity<>(
					encuestaService.obtenerPersonas(), HttpStatus.OK);
		}catch(Exception e) {
			LOG.error("Error: ",e);
			return new ResponseEntity<>(new ArrayList<Persona>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="guardarpersona", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta> guardar(
			@RequestBody PersonaInput persona){
		try {
			encuestaService.guardarPersona(persona);
			return new ResponseEntity<>(new Respuesta("OK"), HttpStatus.OK);
		}catch(Exception e) {
			LOG.error("Error: ",e);
			return new ResponseEntity<>(new Respuesta("ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
