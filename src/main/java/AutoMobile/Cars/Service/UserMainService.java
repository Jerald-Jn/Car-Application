package AutoMobile.Cars.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.User;
import AutoMobile.Cars.Repository.UserRepo;

@Service
public class UserMainService {

    UserRepo userRepo;
    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(8);

    public UserMainService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(User user, UUID uuid) {
        List<User> users=getAll();
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUserName().equals(user.getUserName())){
                throw new CustomRuntimeException("User's alrady stored");
            }
        }
        user.setUserId(uuid);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set roles =new HashSet<String>();
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            roles.add("ROLE_USER");
            user.setRoles(roles);
        }
        return userRepo.save(user);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getByID(String userName) {
        User user= userRepo.findById(userName).get();
        System.err.println("user.getRoles()"+user.getRoles());
        return user;
    }

    public User delete(String userName) {
        User tempUser = new User();
        tempUser = getByID(userName);
        userRepo.deleteById(userName);
        if (tempUser != null) {
            System.err.println("delete successfully");
        }
        return tempUser;
    }

}