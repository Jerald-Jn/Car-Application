package AutoMobile.Cars.Controller;

import java.util.UUID;

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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cart")
@CrossOrigin
@Slf4j
public class CartController {

    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody CartRequest cartRequest){
        CartResponse cartResponse=null;
        try {
            log.info("cartRequest : {}",cartRequest);
            cartResponse=cartService.createCart(cartRequest);
        } catch (Exception e) {
            throw new CustomRuntimeException("create cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @GetMapping("/increase/{carId}")
    public ResponseEntity<?> increaseQuantity(@PathVariable UUID carId){
        log.info("carId : {}",carId);
        CartResponse cartResponse=null;
        try {
            cartResponse=cartService.increaseQuantity(carId);
        } catch (Exception e) {
            throw new CustomRuntimeException("increase cart quantity error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/remove/{carId}")
    public ResponseEntity<?> removeCart(@PathVariable UUID carId){
        log.info("carId : {}",carId);
        CartResponse cartResponse=null;
        try {
            cartResponse=cartService.removeCart(carId);
        } catch (Exception e) {
            throw new CustomRuntimeException("remove cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @GetMapping("")
    public ResponseEntity<?> getCart(){
        CartResponse cartResponse=null;
        try {
            log.info("getCart()");
            cartResponse=cartService.getCart();
        } catch (Exception e) {
            throw new CustomRuntimeException("create cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/clear-cart")
    public String clearCart(){
        log.info("clearCart()");
        return cartService.clearCart();
    }

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<?> clearSpecficItem(@PathVariable UUID carId){
        log.info("carId : {}",carId);
        String cartResponse=null;
        try {
            cartResponse=cartService.clearSpecficItem(carId);
        } catch (Exception e) {
            throw new CustomRuntimeException("remove cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

}
