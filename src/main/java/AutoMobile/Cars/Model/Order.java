package AutoMobile.Cars.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "Car-Order")
public class Order {
    @Id
    String id;
    String first_name;
    String last_name;
    long amount;
    UserInfo userInfo;
    String transactionId;
    String latest_charge;
    String status;
    String paymentMethod;
    String clientSecret;
}
