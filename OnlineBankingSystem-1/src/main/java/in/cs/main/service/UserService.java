package in.cs.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cs.main.entities.Users;
import in.cs.main.repository.UserRepository;

@Service
public class UserService {
	
    @Autowired
	private UserRepository userrepo;
	
    
    public Users createUser(Users user) {
    	
    	if (userrepo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

    	
        user.setRole("CUSTOMER");
		return userrepo.save(user);
    	
    }
    public Users findByUsername(String username) {

        return userrepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    
    public Users findById(Integer userId) {

        return userrepo.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
    
 
    public List<Users> getAllUsers() {

        return userrepo.findAll();
    }

    
    public void deleteUser(Integer userId) {

        if (!userrepo.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        userrepo.deleteById(userId);
    }
    
}
