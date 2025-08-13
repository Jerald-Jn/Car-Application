package AutoMobile.Cars.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AutoMobile.Cars.Model.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {
    Optional<Cart> findByUser(String userName);
}
