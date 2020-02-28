package com.proyectoaws.login.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.ConfirmForgotPasswordRequest;
import com.amazonaws.services.cognitoidp.model.GlobalSignOutRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.PasswordResetRequiredException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.proyectoaws.login.dto.RenewPasswordFirstDTO;
import com.proyectoaws.login.dto.RespuestaApi;
import com.proyectoaws.login.dto.UpdatePasswordDTO;
import com.proyectoaws.login.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	private static final String NO_SE_PUDO_AUTENTICAR = "No se pudo autenticar";

	private static final String USUARIO_NO_AUTORIZADO = "Usuario no autorizado";

	private static final String ERROR = "ERROR";

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Value("${clientId}")
	private String cognitoClienteId;

	@Value("${userPoolId}")
	private String cognitoPoolId;

	@Autowired
	private AWSCognitoIdentityProviderClient cognitoClient;
	
	@Override
	public RespuestaApi getToken(String username, String password) {
		RespuestaApi rpta = new RespuestaApi();

		Map<String, String> authParams = new HashMap<>();
		authParams.put("USERNAME", username);
		authParams.put("PASSWORD", password);

		try {
			AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
					.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
					.withAuthParameters(authParams)
					.withClientId(cognitoClienteId)
					.withUserPoolId(cognitoPoolId);
			log.error("Cognito access : "+cognitoPoolId + " "+cognitoClienteId);
			log.info("Cognito access : "+cognitoPoolId + " "+cognitoClienteId);
			AdminInitiateAuthResult authResponse = cognitoClient.adminInitiateAuth(authRequest);
			
			if (authResponse.getChallengeName() == null || authResponse.getChallengeName().isEmpty()) {
				authResponse.getAuthenticationResult().getAccessToken();
				rpta.setStatus("OK");
				rpta.setAccessToken(authResponse.getAuthenticationResult().getAccessToken());
				rpta.setIdToken(authResponse.getAuthenticationResult().getIdToken());
				rpta.setRefreshToken(authResponse.getAuthenticationResult().getRefreshToken());
				rpta.setBody("Autenticacion correcta");
			} else if (ChallengeNameType.NEW_PASSWORD_REQUIRED.name().equals(authResponse.getChallengeName())) {
				rpta.setStatus("OK-UPDATE");
				rpta.setBody("nuevo password requerido");
				rpta.setSessionId(authResponse.getSession());
			}
		} catch (NotAuthorizedException | UserNotFoundException e) {
			rpta.setStatus(ERROR);
			rpta.setBody(USUARIO_NO_AUTORIZADO);
		} catch(PasswordResetRequiredException e) {
			rpta.setBody("Reinicie su password");
			rpta.setStatus("OK-RESET");
		}

		return rpta;
	}
	
	@Override
	public RespuestaApi resetNewPasswordFirst(RenewPasswordFirstDTO updatePassword) {
		RespuestaApi rpta = new RespuestaApi();
		rpta.setStatus(ERROR);
		rpta.setBody(NO_SE_PUDO_AUTENTICAR);

		Map<String, String> challengeResponses = new HashMap<>();
		challengeResponses.put("USERNAME", updatePassword.getUsername());
		challengeResponses.put("NEW_PASSWORD", updatePassword.getPassword());

		try {

			AdminRespondToAuthChallengeRequest request = new AdminRespondToAuthChallengeRequest()
					.withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
					.withChallengeResponses(challengeResponses)
					.withClientId(cognitoClienteId)
					.withUserPoolId(cognitoPoolId)
					.withSession(updatePassword.getSession());

			AdminRespondToAuthChallengeResult authResponse = cognitoClient.adminRespondToAuthChallenge(request);
			if (authResponse.getChallengeName() == null || authResponse.getChallengeName().isEmpty()) {
				authResponse.getAuthenticationResult().getAccessToken();
				rpta.setStatus("OK");
			}
		} catch (NotAuthorizedException | UserNotFoundException e) {
			rpta.setBody(USUARIO_NO_AUTORIZADO);
		} 

		return rpta;
	}
	
	@Override
	public RespuestaApi updatePassword(UpdatePasswordDTO updatePassword) {
		RespuestaApi rpta = new RespuestaApi();
		rpta.setStatus(ERROR);
		rpta.setBody(NO_SE_PUDO_AUTENTICAR);

		try {
			
			ConfirmForgotPasswordRequest confirmForgotPasswordRequest = new ConfirmForgotPasswordRequest()
					.withClientId(cognitoClienteId)
					.withConfirmationCode(updatePassword.getVerificationCode())
					.withPassword(updatePassword.getNewPassword())
					.withUsername(updatePassword.getUsername());
			
			cognitoClient.confirmForgotPassword(confirmForgotPasswordRequest);
		    
		    rpta.setStatus("OK");
			rpta.setBody("Clave cambiada correctamente");
		} catch (NotAuthorizedException | UserNotFoundException e) {
			rpta.setBody(USUARIO_NO_AUTORIZADO);
		} catch(Exception e) {
			log.error("[updatePassword] Ocurrio un error inesperado: ", e);
			rpta.setBody("Ocurrio un error inesperado");
		}

		return rpta;
	}
	
	/**
	 * Se puede ingresar el accessToken o idToken
	 */
	@Override
	public RespuestaApi signOut(String token) {
		RespuestaApi rpta = new RespuestaApi();
		rpta.setStatus(ERROR);
		rpta.setBody(NO_SE_PUDO_AUTENTICAR);
		
		try {
			GlobalSignOutRequest rq = new GlobalSignOutRequest()
					.withAccessToken(token);
			cognitoClient.globalSignOut(rq);
			rpta.setStatus("OK");
			rpta.setBody("SignOut correcto");
		}catch(Exception e) {
			log.error("[signOut] Ocurrio un error inesperado: ", e);
			rpta.setBody(e.getMessage());
		}
		
		return rpta;
	}

	/**
	 * Se necesita el refreshToken como input
	 */
	@Override
	public RespuestaApi refreshToken(String token) {
		RespuestaApi rpta = new RespuestaApi();
		rpta.setStatus(ERROR);
		rpta.setBody(NO_SE_PUDO_AUTENTICAR);

		Map<String, String> authParams = new HashMap<>();
		authParams.put("REFRESH_TOKEN", token);

		try {
			InitiateAuthRequest authRequest = new InitiateAuthRequest()
					.withAuthFlow(AuthFlowType.REFRESH_TOKEN_AUTH)
					.withAuthParameters(authParams)
					.withClientId(cognitoClienteId);

			InitiateAuthResult authResponse = cognitoClient.initiateAuth(authRequest);
			
			if (authResponse.getChallengeName() == null || authResponse.getChallengeName().isEmpty()) {
				authResponse.getAuthenticationResult().getAccessToken();
				rpta.setStatus("OK");
				rpta.setAccessToken(authResponse.getAuthenticationResult().getAccessToken());
				rpta.setIdToken(authResponse.getAuthenticationResult().getIdToken());
				rpta.setBody("actualizacion token correcta");
			}
		} catch (NotAuthorizedException | UserNotFoundException e) {
			rpta.setBody(USUARIO_NO_AUTORIZADO);
		} catch(PasswordResetRequiredException e) {
			rpta.setBody("Reinicie su password");
			rpta.setStatus("OK-RESET");
		}

		return rpta;
	}
}
