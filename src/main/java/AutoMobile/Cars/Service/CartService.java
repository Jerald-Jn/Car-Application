package AutoMobile.Cars.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import AutoMobile.Cars.Model.Cart;
import AutoMobile.Cars.Repository.CartRepository;
import AutoMobile.Cars.Util.DataConverter;
import AutoMobile.Cars.Util.cart.CartResponse;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;
    
   public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName(); // username
        }
        return null;
    }


    public CartResponse createCart(String carId){
        Optional<Cart> exitingCart=cartRepository.findByUser(getCurrentUsername());
        Cart cart=exitingCart.orElseGet(()->new Cart(getCurrentUsername(), new HashMap<>()));
        Map<String,Integer> items=cart.getItems();
        items.put(carId, items.getOrDefault(carId, 0)+1);
        cart.setItems(items);
        cart=cartRepository.save(cart);
        return DataConverter.convertToCartResponse(cart);
    }

    public CartResponse removeCart(String carId){
        Optional<Cart> exitingCart=cartRepository.findByUser(getCurrentUsername());
        Cart cart=exitingCart.orElseGet(()->new Cart(getCurrentUsername(), new HashMap<>()));
        Map<String,Integer> items=cart.getItems();
        int quantity=items.get(carId);
        if(quantity>1){
            items.put(carId, quantity-1);
        }else{
            items.remove(carId);
        }
        cart.setItems(items);
        cart=cartRepository.save(cart);
        return DataConverter.convertToCartResponse(cart);
    }

    public CartResponse getCart(){
        Optional<Cart> exitingCart=cartRepository.findByUser(getCurrentUsername());
        Cart cart=exitingCart.orElseGet(()->new Cart(getCurrentUsername(), new HashMap<>()));
        return DataConverter.convertToCartResponse(cart);
    }
}
