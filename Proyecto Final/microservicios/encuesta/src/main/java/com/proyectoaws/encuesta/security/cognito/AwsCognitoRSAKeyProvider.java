package com.proyectoaws.encuesta.security.cognito;

import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;

public class AwsCognitoRSAKeyProvider implements RSAKeyProvider {
	private final URL awsKidStoreUrl;
	
	public AwsCognitoRSAKeyProvider(String awsCognitoRegion, String awsUserPoolsId) {
		try {
			URL urlMyCognitoSecret = new URL("https://cognito-idp."+awsCognitoRegion+".amazonaws.com/"+awsUserPoolsId+"/.well-known/jwks.json");
			this.awsKidStoreUrl = urlMyCognitoSecret;
		}catch(Exception e) {
			throw new RuntimeException(String.format("Invalid URL provided, URL"));
		}
	}
	
	@Override
	public RSAPublicKey getPublicKeyById(String kid) {
		try {
			JwkProvider provider = new JwkProviderBuilder(awsKidStoreUrl).build();
			Jwk jwk = provider.get(kid);
			return (RSAPublicKey) jwk.getPublicKey();
		} catch (Exception e) {
			throw new RuntimeException(
					String.format("Failed to get JWT kid=%s from aws_kid_store_url=%s", kid, awsKidStoreUrl));
		}
	}

	@Override
	public RSAPrivateKey getPrivateKey() {
		return null;
	}

	@Override
	public String getPrivateKeyId() {
		return null;
	}
}
