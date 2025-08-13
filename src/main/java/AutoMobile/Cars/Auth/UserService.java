package AutoMobile.Cars.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;

@Service
public class UserService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;

    @Autowired
    UserService(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    public String login(String userName, String password) {
        try {
            /*
             * new UsernamePasswordAuthenticationToken(userName, password) -> This creates an unauthenticated token (with credentials only).
             * authenticationManager.authenticate(...) -> This delegates to an AuthenticationProvider (usually DaoAuthenticationProvider).
             * It calls:
                    *  UserDetailsService.loadUserByUsername(...)
                    *  PasswordEncoder.matches(...) to verify the password
             */
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            // This check the authentication is autheticated
            if (authentication.isAuthenticated()) {
                String token=jwtService.generateToken(userName);
                return token;
            } else {
                return "Invalid credentials";
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'login' -> " + e.getMessage());
        }
    }

}
