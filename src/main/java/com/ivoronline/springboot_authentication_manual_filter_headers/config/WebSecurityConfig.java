package com.ivoronline.springboot_authentication_manual_filter_headers.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private MyFilter myFilter;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeRequests().antMatchers("/Authenticate").permitAll();               //Anonymous
    httpSecurity.authorizeRequests().anyRequest().authenticated();                           //Authenticated
    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //No Session
    httpSecurity.addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class);      //Add Filter
    httpSecurity.csrf().disable();                                                           //Enables POST
  }

}
