package com.hugodev.user_login.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hugodev.user_login.auth.CustomAuthenticationEntryPoint;
import com.hugodev.user_login.auth.JwtAuthFilter;
import com.hugodev.user_login.services.JpaUserDetailsService;
import com.hugodev.user_login.services.init.DbInitializer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JpaUserDetailsService jpaUserDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint; //  injeta o handler
    private final JwtAuthFilter jwtAuthFilter;

    
    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, JwtAuthFilter jwtAuthFilter) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/login", "/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN") // <-- ADICIONADO
                .anyRequest().authenticated()); // Todas as outras requisições exigem autenticação
        
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(exceptions -> exceptions
            .authenticationEntryPoint(customAuthenticationEntryPoint)); // <-- usa o handler customizado
        
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
    public CommandLineRunner run(DbInitializer dbInitializer) {
        return args -> {
            dbInitializer.initializeDatabase();
        };
    }
    
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	     return authConfig.getAuthenticationManager();
	 }


}
