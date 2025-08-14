package AutoMobile.Cars.Util.order;

import AutoMobile.Cars.Model.UserInfo;
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
public class OrderResponse {
    String id;
    String first_name;
    String last_name;
    UserInfo userInfo;
    String transactionId;
    String latest_charge;
    String status;
    long amount;
    String paymentMethod;
    String clientSecret;
}
