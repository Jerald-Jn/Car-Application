package AutoMobile.Cars.Util.payment;

import AutoMobile.Cars.Model.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentRequest {
    // String id;
    String firstName;
    String lastName;
    long amount;
    UserInfo userInfo;
}
