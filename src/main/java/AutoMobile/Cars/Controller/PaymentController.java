package AutoMobile.Cars.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/payments")
@CrossOrigin()
public class PaymentController {

    @Value("${stripe.secretKey}")
    private String secretKey;

    @PostMapping("/create-payment")
    public Map<String, String> createPayment(@RequestBody customerDetail data) throws Exception {
        long amount = Math.round(data.getAmount() * 100);
        System.out.println("PaymentController.createPayment()");
        System.out.println(data);
        if (amount < 200) { // RM 2 minimum for MYR
            throw new IllegalArgumentException("Amount must be at least RM 2.00");
        }
        Stripe.apiKey = secretKey;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // Stripe takes smallest currency unit (₹1 = 100 paise)
                .setCurrency("myr")
                .addPaymentMethodType("card")
                .addPaymentMethodType("fpx")
                .addPaymentMethodType("grabpay")
                // .addPaymentMethodType("googlepay")
                .setReceiptEmail(String.valueOf(data.getEmail()))
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", paymentIntent.getClientSecret());
        return response;
    }

    @Getter
    @Setter
    public static class customerDetail {
        long amount;
        String email;

        @Override
        public String toString() {
            return "customerDetail [amount=" + amount + ", email=" + email + "]";
        }

    }
}
