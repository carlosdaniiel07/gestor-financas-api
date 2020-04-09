package com.carlos.gestorfinancas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.carlos.gestorfinancas.filters.AuthenticationFilter;
import com.carlos.gestorfinancas.filters.AuthorizationFilter;
import com.carlos.gestorfinancas.utils.JWTUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	private final String[] urlsAcessiveisViaPost = {
		"/auth/esqueci-minha-senha/**",
		"/tasks/**"
	};
	
	private final String[] urlsAcessiveisViaGet = {
		"/status/is-alive"
	};
	
	// Configuração HTTP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CORS
		http.cors();
		
		// CSRF
		http.csrf().disable();
		
		// Permite acesso a todos os endpoints armazenados no vetor 'urlsAcessiveisViaPost' e requisita autorização aos demais endpoints
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, urlsAcessiveisViaPost).permitAll()
			.antMatchers(HttpMethod.GET, urlsAcessiveisViaGet).permitAll()
		.anyRequest().authenticated();
		
		// Back-end não irá salvar sessões
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Filtros (interceptam requests e responses)
		http.addFilter(new AuthenticationFilter(authenticationManager(), jwtUtils));
		http.addFilter(new AuthorizationFilter(authenticationManager(), jwtUtils, userDetailsService));
	}
	
	// Configuração de autenticação do Spring
	@Override
	protected void configure(AuthenticationManagerBuilder authConfig) throws Exception {
		// Aqui informo qual implementação de UserDetailsService deve usada, junto com o algorítimo de hash
		authConfig.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	// Configuração do CORS (Cross-Origin Resource Sharing) - permite acesso aos endpoints da aplicação através de qualquer origem
	@Bean
	public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
	
	// BCrypt (algorítimo para hash de senhas)
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
