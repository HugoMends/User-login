package com.hugodev.user_login.auth; 

import com.fasterxml.jackson.databind.ObjectMapper; 
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component 
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Define o status da resposta como 401 Unauthorized
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        // Define o tipo de conteúdo como JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Cria o corpo da resposta de erro
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", Instant.now().toString());
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", "Credenciais de autenticação inválidas.");
        errorDetails.put("path", request.getRequestURI());

        // Escreve o JSON na resposta
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, errorDetails);
        out.flush();
    }
}