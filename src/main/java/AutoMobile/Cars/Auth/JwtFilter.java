package AutoMobile.Cars.Auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    JwtService jwtService;
    @Autowired
    ApplicationContext applicationContext;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Getheader "Authorization" value from the request
        String token= request.getHeader("Authorization");
        String userName=null;
        if(token!=null){
            // We got only token without "Bearer "
            token=token.substring(7);
            // Pass token to the "JwtService"
            userName=jwtService.getUsernameByToken(token);
        }
        /* Check username not null
          Gets the current security context (a per-thread container for security info). 
            Gets the Authentication object, it holds 
            1)authenticated user (principal)
            2)credentials (e.g. password/token)
            3)authorities/roles
        */ 
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            // This used to we got our "UserDetails"
            UserDetails userDetails=applicationContext.getBean(PrincpleUser.class).loadUserByUsername(userName);
            // Check token expiry
            if(jwtService.validateToken(token,userDetails)) {
                /* Creates an authentication token using:
                 * the authenticated user
                 * no credentials (because JWT is stateless, we don't need the password here)
                 * userDetails.getAuthorities(): the roles/permissions (e.g. ROLE_USER, ROLE_ADMIN)
                 */
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				// Adds additional details to the authentication object, those are IP address, Session ID, User agent
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Sets the built authToken into the Spring Security context, marking the user as authenticated for this request thread.
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
        }
        // It passes the request and response to the next filter in the Spring Security filter chain.
        // Without it, the request stops there and never reaches the controller.
        filterChain.doFilter(request,response);
    }
    
}
