package com.hugodev.user_login.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {

	@Value("${jwt.secret-key}") // Injeta o valor da chave secreta do application.properties
	private String jwtSecretKey;

	public String generateToken(Authentication auth) {

		// Converte o objeto de autenticação para um objeto UserDetails
		UserDetails userDetails = (UserDetails) auth.getPrincipal();

		// Pega as roles do usuário
		String roles = userDetails.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority())
				.collect(Collectors.joining(","));
		
		// Cria a assinatura do token
		Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey.getBytes());
		
		 // Constrói e retorna o token JWT
        return JWT.create()
                .withSubject(userDetails.getUsername()) // O "assunto" do token é o email do usuário
                .withClaim("roles", roles) // Adiciona as roles como um "claim" no token
                .withIssuedAt(Instant.now()) // Data de emissão do token
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS)) // Data de expiração (1 hora)
                .sign(algorithm); // Assina o token com a chave secreta
	}
	// Extrai o 'subject' (username/email) do token
    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    // Valida se o token é válido e não expirou
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        // Pega a data de expiração do token e a compara com o tempo atual
        Date expiration = JWT.decode(token).getExpiresAt();
        return expiration.before(Date.from(Instant.now()));
    }
}
