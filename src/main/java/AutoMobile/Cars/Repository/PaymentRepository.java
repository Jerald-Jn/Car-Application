package AutoMobile.Cars.Repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import AutoMobile.Cars.Model.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment,String> {
    @Query("{ 'paymentDetailsMap.?0': { $exists: true } }")
    Payment findByPaymentDetailsMap(Object paymentDetailsMap);
    Payment findByUserId(UUID userId);
}
