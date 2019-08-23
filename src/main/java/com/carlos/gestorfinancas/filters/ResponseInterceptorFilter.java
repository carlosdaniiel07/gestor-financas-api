package com.carlos.gestorfinancas.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 16/08/2019
 */
@Component
public class ResponseInterceptorFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		
		// CORS
		//servletResponse.addHeader("Access-Control-Allow-Origin", "*");
		//servletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization ");
		
		chain.doFilter(request, servletResponse);
	}
}
