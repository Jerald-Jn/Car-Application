package AutoMobile.Cars.Model;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import AutoMobile.Cars.Util.cart.CartItem;
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
@Document(collection = "cart_for_car")
public class Cart {
    @Id
    String id;
    UUID userId;
    Map<UUID,CartItem> items;

    public Cart(UUID userId,Map<UUID,CartItem>  items){
        this.userId=userId;
        this.items=items;
    }

    @Override
    public String toString() {
        return "Cart [id=" + id + ", userId=" + userId + ", items=" + items + "]";
    }

}
