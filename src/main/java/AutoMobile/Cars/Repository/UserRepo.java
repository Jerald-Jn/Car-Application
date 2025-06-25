package AutoMobile.Cars.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AutoMobile.Cars.Model.User;

@Repository
public interface UserRepo extends MongoRepository<User,String> {
    // User findByUserName(String userName);
}
