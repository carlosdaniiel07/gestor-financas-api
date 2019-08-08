package com.carlos.gestorfinancas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final String[] endpointAcessiveisSomenteLeitura = {
			"/categorias/**",
			"/subcategorias/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CORS
		http.cors();
		
		// CSRF
		http.csrf().disable();
		
		// Permite acesso a todos os endpoints armazenados no vetor 'endpointAcessiveis' e requisita autorização aos demais endpoints
		http.authorizeRequests().antMatchers(HttpMethod.GET, endpointAcessiveisSomenteLeitura).permitAll().anyRequest().authenticated();
		
		// Back-end não irá salvar sessões
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	// Configuração do CORS (Cross-Origin Resource Sharing) - permite acesso aos endpoints da aplicação através de qualquer origem
	@Bean
	public CorsConfigurationSource corsConfiguration() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
