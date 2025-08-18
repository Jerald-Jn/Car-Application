package AutoMobile.Cars.Model;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import AutoMobile.Cars.Util.payment.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Car-Payment")
public class Payment {
    @Id
    String id;
    UUID userId;
    String firstName;
    String lastName;
    String name;
    Map<String,PaymentDetails> paymentDetailsMap;
    @Override
    public String toString() {
        return "Order [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + " , orderDetails= "+paymentDetailsMap;
    }
}
