package AutoMobile.Cars.Util.cart;

import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartResponse {
    String id;
    UUID userId;
    Map<UUID,CartItem> items;
    @Override
    public String toString() {
        return "CartResponse [id=" + id + ", userId=" + userId + ", items=" + items + "]";
    }
}
