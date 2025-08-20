package AutoMobile.Cars.Auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Repository.UserRepo;
import AutoMobile.Cars.Security.JwtBlacklist;

@RestController
@CrossOrigin()
public class UserController {

    UserService service;

    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtBlacklist jwtBlacklist;
   

    public UserController(UserService service){
        this.service=service;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password){
        try {
            return new ResponseEntity<>(service.login(userName, password), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new CustomRuntimeException(e);
        }
        
    }

    
    
}
