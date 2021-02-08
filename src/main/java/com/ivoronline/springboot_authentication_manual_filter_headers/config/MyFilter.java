package com.ivoronline.springboot_authentication_manual_filter_headers.config;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MyFilter implements Filter {

  @Autowired MyAuthenticationManager myAuthenticationManager;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
    throws IOException, ServletException {

    //CAST TO GET ACCESS TO HEADERS
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    //GET CREDENTIALS
    String enteredUsername = httpRequest.getHeader("enteredUsername");
    String enteredPassword = httpRequest.getHeader("enteredPassword");

    //AUTHENTICATE
    Authentication enteredAuth  = new UsernamePasswordAuthenticationToken(enteredUsername, enteredPassword);
    Authentication returnedAuth = myAuthenticationManager.authenticate(enteredAuth);

    //STORE AUTHENTICATION INTO CONTEXT (SESSION)
    SecurityContextHolder.getContext().setAuthentication(returnedAuth);

    //FORWARD REQUEST
    filterchain.doFilter(request, response);

  }

}
