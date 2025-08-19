package AutoMobile.Cars.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Service.CartService;
import AutoMobile.Cars.Util.cart.CartRequest;
import AutoMobile.Cars.Util.cart.CartResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cart")
@CrossOrigin()
@AllArgsConstructor
public class CartController {

    @Autowired
    CartService cartService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody CartRequest cartRequest){
        CartResponse cartResponse=null;
        try {
            System.err.println(cartRequest);
            cartResponse=cartService.createCart(cartRequest);
        } catch (Exception e) {
            throw new CustomRuntimeException("create cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/remove/{carId}")
    public ResponseEntity<?> removeCart(@PathVariable UUID carId){
        CartResponse cartResponse=null;
        try {
            System.err.println(carId);
            cartResponse=cartService.removeCart(carId);
        } catch (Exception e) {
            throw new CustomRuntimeException("create cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @GetMapping()
    public ResponseEntity<?> getCart(){
        CartResponse cartResponse=null;
        try {
            System.out.println("CartController.getCart()");
            cartResponse=cartService.getCart();
            System.err.println(cartResponse);
        } catch (Exception e) {
            throw new CustomRuntimeException("create cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllCart(){
        List<CartResponse> cartResponse=null;
        try {
            cartResponse=cartService.getAllCart();
        } catch (Exception e) {
            throw new CustomRuntimeException("get cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/clear-cart")
    public String clearCart(){
        return cartService.clearCart();
    }
}
