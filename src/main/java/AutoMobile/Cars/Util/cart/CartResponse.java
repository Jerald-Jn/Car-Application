package AutoMobile.Cars.Util.cart;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartResponse {
    String id;
    String user;
    Map<String,Integer> items;
    @Override
    public String toString() {
        return "CartResponse [id=" + id + ", user=" + user + ", items=" + items + "]";
    }
}
