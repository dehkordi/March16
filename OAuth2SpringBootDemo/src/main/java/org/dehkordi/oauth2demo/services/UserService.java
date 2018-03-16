package org.dehkordi.oauth2demo.services;

import java.util.List;

import org.dehkordi.oauth2demo.entities.User;
import org.dehkordi.oauth2demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository repo;

//    @Bean
//    public PasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    public User save(User user){
//        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
    	for (User u : repo.findAll()) {
    		if (u.getUsername().equals(user.getUsername())) {
    			return null;
    		}
    	}
        user.setPassword(user.getPassword());
        return repo.save(user); 
    }
    
    public User getUserByName(String username) {
    	return repo.findByUsername(username);
    }
    
    public List<User> users() {
    	return repo.findAll();
    }
    
    public boolean delete(String username) {
    	User u = null;
    	for (User user : repo.findAll()) {
    		if (user.getUsername().equals(username)) {
    			u = user;
    			break;
    		}
    	}
    	if (u != null) {
    		repo.delete(u);
    		return true;
    	} else {
    		return false;
    	}
    		
    }

}
