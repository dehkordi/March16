package org.dehkordi.oauth2demo.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.dehkordi.oauth2demo.entities.User;
import org.dehkordi.oauth2demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserServiceController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/")
	public String index() {
		return "Hello world";
	}

	@GetMapping(value = "/private")
	public String privateArea() {
		return "Private area";
	}

	@GetMapping(value ="/owndata")
	public User ownData() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.getUserByName(auth.getName());
	}
	
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/home")
	public String home() {
		return "home";
	}
	
	@DeleteMapping(value = "/delete/{username}")
	public boolean delete(@PathVariable String username) {
		return userService.delete(username);
	}
	
	@GetMapping(value = "/users")
	public List<User> users() {
		return userService.users();
	}
	
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal User user) {
        return "admin";
    }
    
	@PostMapping(value = "/register")
	public User register(@RequestBody User user) {
		System.out.println(user);
		return userService.save(user);
	}
	
	@GetMapping("/date")
	public String date() {
		return "Hi, today is: " + new Date();
	}
	
	@GetMapping("/time")
	public String time() {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalTime localTime = localDateTime.toLocalTime();
		return "Hi, current time is: " + localTime;
	}

// End point defined in security within the application.yml 
//	// grant_type=password&username=user&password=user
//	//@PathVariable String grant_type, @PathVariable String username, @PathVariable String password
//	@GetMapping(value = "/oauth/token")
//	public String token() {
////		System.out.println(grant_type + " " +  username + " " + password);
//		return "token";
//	}

}
