package in.cs.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cs.main.entities.Users;
import in.cs.main.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	
	@Autowired
    private  UserService userService;

  

    
    // REGISTER USER
    

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(
            @RequestBody Users user) {

        Users registeredUser =
                userService.registerUser(user);

        return ResponseEntity.ok(registeredUser);
    }

    
    // GET USER BY ID
   

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserById(
            @PathVariable Integer userId) {

        Users user = userService.findById(userId);

        return ResponseEntity.ok(user);
    }

    
    // GET USER BY USERNAME
    

    @GetMapping("/username/{username}")
    public ResponseEntity<Users> getUserByUsername(
            @PathVariable String username) {

        Users user =
                userService.findByUsername(username);

        return ResponseEntity.ok(user);
    }

    
    // GET ALL USERS
    

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    
    // DELETE USER
    

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Integer userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }
}

