package AutoMobile.Cars.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.User;
import AutoMobile.Cars.Repository.UserRepo;

@Service
public class UserMainService {

    UserRepo userRepo;
    InsuranceService insuranceService;
    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(8);

    public UserMainService(UserRepo userRepo, InsuranceService insuranceService) {
        this.userRepo = userRepo;
        this.insuranceService = insuranceService;
    }

    public User addUser(User user, UUID uuid) {
        List<User> check=getAll();
        for(int i=0;i<check.size();i++){
            if(check.get(i).getUserName().equals(user.getUserName())){
                throw new CustomRuntimeException("User's alrady stored");
            }
        }
        user.setUserId(uuid);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // user=set(user,uuid);
        return userRepo.save(user);
    }

    // public User set(User user, UUID uuid){
    //     byte[] b = new byte[8];
    //     new SecureRandom().nextBytes(b);
    //     user.getCar().setCarId(Base64.getEncoder().encodeToString(b));
    //     Car car=new Car();
    //     car=user.getCar();
    //     Insurance insurance = insuranceService.setInsuranceTemp(car.getInsurance());
    //     car.setInsurance(insurance);
    //     user.setUserId(uuid);
    //     user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    //     return user;
    // }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getByID(String userName) {
        return userRepo.findById(userName).get();
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