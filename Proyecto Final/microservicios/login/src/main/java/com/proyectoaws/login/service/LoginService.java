package com.proyectoaws.login.service;

import com.proyectoaws.login.dto.RenewPasswordFirstDTO;
import com.proyectoaws.login.dto.RespuestaApi;
import com.proyectoaws.login.dto.UpdatePasswordDTO;

public interface LoginService {
	public RespuestaApi getToken(String username, String password);
	public RespuestaApi resetNewPasswordFirst(RenewPasswordFirstDTO updatePassword);
	public RespuestaApi updatePassword(UpdatePasswordDTO updatePassword);
	public RespuestaApi signOut(String token);
	public RespuestaApi refreshToken(String token);
}
