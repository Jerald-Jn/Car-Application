package AutoMobile.Cars.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomLogout implements LogoutHandler {

    JwtBlacklist jwtBlacklist;

    public CustomLogout(JwtBlacklist jwtBlacklist) {
        this.jwtBlacklist = jwtBlacklist;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    log.info("request : {}, response : {}, authentication : {}",request,response,authentication);
        try {
            final String token = request.getHeader("Authorization");
            boolean chceklogout = false;
        if (token != null) {
            if(token.startsWith("Bearer ")){
                String tempToken = token.substring(7);
                chceklogout=jwtBlacklist.blackToken(tempToken);
            }else if (token.startsWith("Basic ")) {
                String tempToken=token.substring(6);
                chceklogout=jwtBlacklist.blackToken(tempToken);
            }
        }
        if(chceklogout){
            return;
        }
        if (authentication != null && authentication.getName() != null) {
            log.info("User logged out: {}" + authentication.getName());
        } else {
            log.info("Anonymous logout attempt : {}"+authentication);
        }
        }
         catch (Exception e) {
            throw new CustomRuntimeException(e);
        }
    }
    
}
