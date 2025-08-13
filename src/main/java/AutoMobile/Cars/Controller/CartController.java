package AutoMobile.Cars.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Service.CartService;
import AutoMobile.Cars.Util.cart.CartResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cart")
@CrossOrigin
@AllArgsConstructor
public class CartController {

    @Autowired
    CartService cartService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody String carId){
        CartResponse cartResponse=null;
        try {
            cartResponse=cartService.createCart(carId);
        } catch (Exception e) {
            throw new CustomRuntimeException("create cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeCart(@RequestBody String carId){
        CartResponse cartResponse=null;
        try {
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
            cartResponse=cartService.getCart();
        } catch (Exception e) {
            throw new CustomRuntimeException("create cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }
}
