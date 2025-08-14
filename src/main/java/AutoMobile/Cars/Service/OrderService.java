package AutoMobile.Cars.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import AutoMobile.Cars.Model.Order;
import AutoMobile.Cars.Repository.OrderRepository;
import AutoMobile.Cars.Util.DataConverter;
import AutoMobile.Cars.Util.order.OrderRequest;
import AutoMobile.Cars.Util.order.OrderResponse;


@Service
public class OrderService {
    
    @Value("${stripe.secretKey}")
    private String secretKey;

    @Autowired
    DataConverter dataConverter;

    @Autowired
    OrderRepository orderRepository;
    

    public OrderResponse createPayment(OrderRequest orderRequest) throws StripeException{
        
        Stripe.apiKey = secretKey;
        
        long amount = Math.round(orderRequest.getAmount() * 1000);
        System.out.println("PaymentController.createPayment()");
        System.out.println(amount+" "+orderRequest);
        if (amount < 200) { // RM 2 minimum for MYR
            throw new IllegalArgumentException("Amount must be at least RM 2.00");
        }
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // Stripe takes smallest currency unit (₹1 = 100 paise)
                .setCurrency("myr")
                .addPaymentMethodType("card")
                .addPaymentMethodType("fpx")
                .addPaymentMethodType("grabpay")
                .setReceiptEmail(String.valueOf(orderRequest.getUserInfo().getEmail()))
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        Order order=dataConverter.convertTOrder(paymentIntent,orderRequest);
        order=orderRepository.save(order);
        return dataConverter.convertTOrderResponse(order);
    } 
}
