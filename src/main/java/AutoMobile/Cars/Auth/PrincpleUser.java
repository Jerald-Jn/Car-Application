package AutoMobile.Cars.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.User;

import AutoMobile.Cars.Repository.UserRepo;

@Component
public class PrincpleUser implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Find User from username
            User user = userRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            if(user!=null){
                return new UserDetailsTemp(user); 
            }
            return null;
        } catch (Exception e) {
            throw new CustomRuntimeException("Get exception 'loadUserByUsername'"+e.getMessage());
        }
            
    }

}
