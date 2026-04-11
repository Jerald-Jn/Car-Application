package AutoMobile.Cars.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.Payment;
import AutoMobile.Cars.Service.CarService;
import AutoMobile.Cars.Service.CartService;
import AutoMobile.Cars.Service.PaymentService;
import AutoMobile.Cars.Service.UserMainService;
import AutoMobile.Cars.Util.cart.CartResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@CrossOrigin( origins = "*")
@Slf4j
public class AdminController {

    UserMainService userMainService;
    CarService carService;
    CartService cartService;
    PaymentService paymentService;

    public AdminController(UserMainService userMainService, CarService carService, CartService cartService,
            PaymentService paymentService) {
        this.userMainService = userMainService;
        this.carService = carService;
        this.cartService = cartService;
        this.paymentService = paymentService;
    }

    @GetMapping("/all/users")
    public ResponseEntity<?> get() throws Exception {
        try {
            log.info(" get users");
            return new ResponseEntity<>(userMainService.getAll(), HttpStatus.OK);
        } catch (RuntimeException r) {
            throw new CustomRuntimeException("Runtime Error -> " + r);
        } catch (Exception e) {
            throw new CustomException("error -> " + e);
        }
    }

    @DeleteMapping("/delete/user")
    public ResponseEntity<?> delete(@RequestParam(required = true) String userName) throws Exception {
        try {
            log.info(" delete userName : {}", userName);
            return new ResponseEntity<>(userMainService.delete(userName), HttpStatus.ACCEPTED);
        } catch (RuntimeException r) {
            throw new CustomRuntimeException("Runtime Error -> " + r);
        } catch (Exception e) {
            throw new CustomException("error -> " + e);
        }

    }

    @PostMapping("/car/add")
    public ResponseEntity<?> addCar(@RequestPart("cars") String jsonStringcar,
            @RequestPart("files") List<MultipartFile> images,
            @RequestPart("banner") MultipartFile carImage, @RequestPart("logo") MultipartFile carLogo)
            throws CustomException {
        try {
            log.info("jsonStringcar : {}, images : {},  carImage : {}, carLogo : {}",jsonStringcar, images, carImage, carLogo);
            return new ResponseEntity<>(carService.addCar(jsonStringcar, images, carImage, carLogo),
                    HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/all/cart")
    public ResponseEntity<?> getAllCart() {
        List<CartResponse> cartResponse = null;
        try {
            log.info("getAllCart()");
            cartResponse = cartService.getAllCart();
        } catch (Exception e) {
            throw new CustomRuntimeException("get cart error");
        }
        return ResponseEntity.ok().body(cartResponse);
    }

    @GetMapping("/all/payments")
    public ResponseEntity<?> getAllPayment() throws CustomException{
        try {
            log.info("getAllPayment()");
            List<Payment> payment = paymentService.getAllPayment();
            return new ResponseEntity<>(payment, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        
    }
}
