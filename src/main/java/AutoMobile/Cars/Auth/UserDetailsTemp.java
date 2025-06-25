package AutoMobile.Cars.Auth;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import AutoMobile.Cars.Model.User;

public class UserDetailsTemp implements UserDetails {

    User user=new User();

    public UserDetailsTemp(User user) {
        this.user = user;
    }
    /* 
        Spring Security calls them internally during authentication and authorization.
        They are used by:
            UserDetailsService
            Authentication providers (like DaoAuthenticationProvider)
            JWT token filters (when setting authentication in the security context)
     */  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        String userName = user.getPassword();
        return userName;
    }

    @Override
    public String getUsername() {
            String userName = user.getUserName();
            return userName;
       
    }

}
