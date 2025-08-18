package AutoMobile.Cars.Util.cart;

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
public class CartItem {
    
    String imageUrl;
    int price;
    int quantity;
    String model;

    @Override
    public String toString() {
        return "CartItem [imageUrl=" + imageUrl + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
