package AutoMobile.Cars.Repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AutoMobile.Cars.Model.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment,String> {
    Payment findByUserId(UUID userId);
}
