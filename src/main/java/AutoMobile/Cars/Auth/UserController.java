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

    // @GetMapping("/logout")
    // public ResponseEntity<?> logout(HttpServletRequest request) {
    //     try {
    //         final String token = request.getHeader("Authorization");
    //         boolean chceklogout = false;
    //     if (token != null) {
    //         if(token.startsWith("Bearer ")){
    //             String tempToken = token.substring(7);
    //             chceklogout=jwtBlacklist.blackToken(tempToken);
    //         }else if (token.startsWith("Basic ")) {
    //             String tempToken=token.substring(6);
    //             chceklogout=jwtBlacklist.blackToken(tempToken);
    //         }
    //     }
    //     if(chceklogout){
    //             return new ResponseEntity<>("Logout successfully",HttpStatus.ACCEPTED);
    //         }
    //     return new ResponseEntity<>("Pls logout correctly", HttpStatus.BAD_REQUEST);
    //     } catch (Exception e) {
    //         throw new CustomRuntimeException(e);
    //     }
    // }
    
}
