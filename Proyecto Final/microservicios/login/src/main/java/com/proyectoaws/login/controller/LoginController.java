package com.proyectoaws.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoaws.login.dto.BasicAccessDTO;
import com.proyectoaws.login.dto.LoginDTO;
import com.proyectoaws.login.dto.RenewPasswordFirstDTO;
import com.proyectoaws.login.dto.RespuestaApi;
import com.proyectoaws.login.dto.UpdatePasswordDTO;
import com.proyectoaws.login.service.LoginService;

@RestController
@CrossOrigin
@RequestMapping("api/security")
public class LoginController {

	
	@Autowired
	private LoginService securityService;
	
	@PostMapping(value="token",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaApi> verificarToken(){
		return new ResponseEntity<>(
				new RespuestaApi("OK", SecurityContextHolder.getContext().getAuthentication().getPrincipal()), HttpStatus.OK
				);
	}
	@PostMapping(value="login",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaApi> login(@RequestBody LoginDTO login){
		return new ResponseEntity<>(
				securityService.getToken(login.getUsername(), login.getPassword()), HttpStatus.OK
				);
	}
	
	@PostMapping(value="first-reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaApi> renewPasswordFirst(
			@RequestBody RenewPasswordFirstDTO updatePassword){
		return new ResponseEntity<>(
				securityService.resetNewPasswordFirst(updatePassword), HttpStatus.OK);
	}
	
	@PostMapping(value="change-password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaApi> updatePassword(
			@RequestBody UpdatePasswordDTO updatePassword){
		return new ResponseEntity<>(
				securityService.updatePassword(updatePassword), HttpStatus.OK);
	}
	
	@PostMapping(value="signout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaApi> signOut(
			@RequestBody BasicAccessDTO token){
		return new ResponseEntity<>(
				securityService.signOut(token.getToken()), HttpStatus.OK);
	}
	
	@PostMapping(value="refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespuestaApi> refreshToken(
			@RequestBody BasicAccessDTO token){
		return new ResponseEntity<>(
				securityService.refreshToken(token.getToken()), HttpStatus.OK);
	}
}
