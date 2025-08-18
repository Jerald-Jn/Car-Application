package AutoMobile.Cars.Util.payment;

import AutoMobile.Cars.Model.UserInfo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
@Data
public class PaymentDetails {
    String Id;
    long amount;
    UserInfo userInfo;
    String latest_charge;
    String status;
    String paymentMethod;
}
