package AutoMobile.Cars.Util.order;

import AutoMobile.Cars.Model.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderRequest {
    String id;
    String first_name;
    String last_name;
    long amount;
    UserInfo userInfo;
}
