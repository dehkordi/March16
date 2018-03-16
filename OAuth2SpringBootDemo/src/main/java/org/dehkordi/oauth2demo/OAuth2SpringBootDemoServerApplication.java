package org.dehkordi.oauth2demo;

import java.util.Arrays;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.dehkordi.oauth2demo.entities.Role;
import org.dehkordi.oauth2demo.entities.User;
import org.dehkordi.oauth2demo.model.CustomUserDetails;
import org.dehkordi.oauth2demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootApplication
public class OAuth2SpringBootDemoServerApplication {

	@Autowired
	public void authentificationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception {

		if (repo.count() == 0) {
			repo.save(new User("admin", "pass", Arrays.asList(new Role("ADMIN"), new Role("USER"), new Role("ACTUATOR"))));
			repo.save(new User("user", "pass", Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
			repo.save(new User("public", "pass", Arrays.asList(new Role("ACTUATOR"))));
		}
//		builder.userDetailsService(s -> new CustomUserDetails(repo.findByUsername(s)));
		
		builder.userDetailsService(new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
				return new CustomUserDetails(repo.findByUsername(s));
			}
		});
	}

	
	public static void main(String[] args) {
		SpringApplication.run(OAuth2SpringBootDemoServerApplication.class, args);
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	  TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
	      @Override
	      protected void postProcessContext(Context context) {
	        SecurityConstraint securityConstraint = new SecurityConstraint();
	        securityConstraint.setUserConstraint("CONFIDENTIAL");
	        SecurityCollection collection = new SecurityCollection();
	        collection.addPattern("/*");
	        securityConstraint.addCollection(collection);
	        context.addConstraint(securityConstraint);
	      }
	    };
	  
	  tomcat.addAdditionalTomcatConnectors(redirectConnector());
	  return tomcat;
	}

	private Connector redirectConnector() {
	  Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	  connector.setScheme("http");
	  connector.setPort(8080);
	  connector.setSecure(true);
	  connector.setRedirectPort(8443);
	  
	  return connector;
	}
}
