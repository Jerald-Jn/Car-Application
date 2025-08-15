package AutoMobile.Cars.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogout implements LogoutHandler {

    @Autowired
    JwtBlacklist jwtBlacklist;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("CustomLogout.logout()");
             try {
            final String token = request.getHeader("Authorization");
            boolean chceklogout = false;
        if (token != null) {
            if(token.startsWith("Bearer ")){
                String tempToken = token.substring(7);
                System.err.println(tempToken);
                chceklogout=jwtBlacklist.blackToken(tempToken);
            }else if (token.startsWith("Basic ")) {
                String tempToken=token.substring(6);
                System.err.println(tempToken);
                chceklogout=jwtBlacklist.blackToken(tempToken);
            }
        }
        if(chceklogout){
            return;
        }
        if (authentication != null && authentication.getName() != null) {
            System.out.println("User logged out: " + authentication.getName());
        } else {
            System.out.println("Anonymous logout attempt"+authentication);
        }
        }
         catch (Exception e) {
            throw new CustomRuntimeException(e);
        }
    }
    
}
