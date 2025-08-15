package AutoMobile.Cars.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AutoMobile.Cars.Model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    Order findByClientSecret(String clientSecret);
}
