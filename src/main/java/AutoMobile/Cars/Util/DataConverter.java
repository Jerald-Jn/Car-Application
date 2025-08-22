package AutoMobile.Cars.Util;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.stripe.model.PaymentIntent;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.Cars;
import AutoMobile.Cars.Model.Cart;
import AutoMobile.Cars.Model.Payment;
import AutoMobile.Cars.Model.User;
import AutoMobile.Cars.Repository.UserRepo;
import AutoMobile.Cars.Util.car.CarResponse;
import AutoMobile.Cars.Util.cart.CartResponse;
import AutoMobile.Cars.Util.payment.PaymentRequest;
import AutoMobile.Cars.Util.payment.PaymentResponse;

@Component
public class DataConverter {

    @Autowired
    UserRepo userRepo;
    
    public static CarResponse carToCarResponse(Cars carEntity){
        return CarResponse.builder().carId(carEntity.getCarId()).model(carEntity.getModel())
        .color(carEntity.getColor()).engineCapacity(carEntity.getEngineCapacity()).fuelType(carEntity.getFuelType()).make(carEntity.getMake())
        .mileage(carEntity.getMileage()).price(carEntity.getPrice()).transmission(carEntity.getTransmission()).vin(carEntity.getVin())
        .year(carEntity.getYear()).carLogo(carEntity.getCarLogo()).carImage(carEntity.getCarImage()).build();
    }

    public CartResponse convertToCartResponse(Cart cart){
        return CartResponse.builder().id(cart.getId()).userId(cart.getUserId()).items(cart.getItems()).build();
    }

    public Payment convertToPayment(PaymentIntent paymentIntent,  PaymentRequest paymentRequest) {
        Payment order= Payment.builder().paymentDetailsMap(new HashMap<>())
                            .build();
        return order;
    }

    public PaymentResponse convertToPaymentResponse(Payment payment) {
        PaymentResponse paymentResponse=PaymentResponse.builder()
                            .id(payment.getId()).userId(payment.getUserId())
                            .paymentDetails(payment.getPaymentDetailsMap())
                            .build();
        System.err.println(paymentResponse);
        return paymentResponse;
    }

    public UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String username= auth.getName(); // username
            System.err.println("username -> "+username);
            Optional<User> user = userRepo.findById(username);
            if(user.isPresent()){
                System.err.println("user -> "+user.get());
                return user.get().getUserId();
            }
        }
        throw new CustomRuntimeException("User not found");
    }

}

   