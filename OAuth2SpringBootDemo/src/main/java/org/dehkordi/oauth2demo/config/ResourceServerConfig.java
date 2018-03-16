package org.dehkordi.oauth2demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 *The @EnableResourceServer annotation adds a filter of type OAuth2AuthenticationProcessingFilter automatically
 *to the Spring Security filter chain.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	
    	http.authorizeRequests().antMatchers("/","/home","/login").permitAll()
    		.antMatchers("/owndata/**", "/private/**")
    		.authenticated()
    		.antMatchers("/users", "/oauth/**").access("hasRole('USER')")
    		.and()
    		.authorizeRequests()
        	.antMatchers("/admin", "/register/**", "/delete/**").access("hasRole('ADMIN')")
        	.and()
        	.csrf()
            .disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    	
//            .exceptionHandling()
//            .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    	
//    	http
//        .authorizeRequests()
//            .antMatchers("/admin/*").hasRole("PERMISSION_ADMIN")                      
//            .and().csrf()
//            .disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    		//.access("#oauth2.hasScope('ADMIN')");
    	// access("#oauth2.hasScope('USER')")
    	
//        http.headers().frameOptions().disable().and()
//                .authorizeRequests()
//                .antMatchers("/","/home","/register","/login").permitAll()
//                .antMatchers("/private/**").authenticated();
    }


}
