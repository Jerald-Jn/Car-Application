package AutoMobile.Cars.Util.payment;

import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    String id;
    String firstName;
    UUID userId;
    String lastName;
    Map<String,PaymentDetails> paymentDetails;
}
