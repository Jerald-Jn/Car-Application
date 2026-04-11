package AutoMobile.Cars.Auth;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Model.User;
import AutoMobile.Cars.Repository.UserRepo;
import AutoMobile.Cars.Security.JwtBlacklist;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    UserService service;
    UserRepo userRepo;
    JwtBlacklist jwtBlacklist;
   

    public UserController(UserService service, UserRepo userRepo, JwtBlacklist jwtBlacklist) {
        this.service = service;
        this.userRepo = userRepo;
        this.jwtBlacklist = jwtBlacklist;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        log.info("user : {}",user);
        return new ResponseEntity<>(service.login(user.getUserName(), user.getPassword()), HttpStatus.ACCEPTED);
    }
}
