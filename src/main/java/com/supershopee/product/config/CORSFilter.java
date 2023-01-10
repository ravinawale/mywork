package com.supershopee.product.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class CORSFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		
		addHeader(response,"Access-Control-Allow-Origin","*");
		addHeader(response,"Access-Control-Allow-Credentials","true");
		addHeader(response,"Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE");
		addHeader(response,"Access-Control-Max-Age","3600");
		addHeader(response,"Access-Control-Allow-Headers","X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");
		chain.doFilter(req, res);
	}
	
	private void addHeader(HttpServletResponse response,String name,String value) {
		Optional<String> userName = Optional.of(response.getHeader(name));
		if(userName.isEmpty()) {
			response.setHeader(name,value);
		}
	}
}