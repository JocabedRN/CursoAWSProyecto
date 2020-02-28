package com.proyectoaws.encuesta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="persona")
@Table
public class Persona implements Serializable{
	
	private static final long serialVersionUID = 2445247993956960711L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idpersona;
	
	@Column(name = "nombres", nullable = false, length = 30)
	private String nombres;
	
	@Column(name = "apellidos", nullable = false, length = 30)
	private String apellidos;
	
	@Column(name = "edad", nullable = false)
	private int edad;
	
	@Column(name = "lenguaje", nullable = false, length = 10)
	private String lenguaje;
	
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getLenguaje() {
		return lenguaje;
	}
	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}
	
}
