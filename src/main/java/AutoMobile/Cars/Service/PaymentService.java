package AutoMobile.Cars.Service;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.Payment;
import AutoMobile.Cars.Model.UserInfo;
import AutoMobile.Cars.Repository.PaymentRepository;
import AutoMobile.Cars.Repository.UserRepo;
import AutoMobile.Cars.Util.DataConverter;
import AutoMobile.Cars.Util.payment.PaymentDetails;
import AutoMobile.Cars.Util.payment.PaymentRequest;
import AutoMobile.Cars.Util.payment.PaymentResponse;


@Service
public class PaymentService {
    
    @Value("${stripe.secretKey}")
    private String secretKey;

    @Autowired
    DataConverter dataConverter;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    UserRepo userRepo;
    

    public PaymentResponse createPayment(PaymentRequest paymentRequest) throws StripeException{
        
        Stripe.apiKey = secretKey;

        UUID userId=dataConverter.getCurrentUserId();
        System.err.println(userId);
        
        long amount = Math.round(paymentRequest.getAmount() * 100);
        System.out.println("PaymentController.createPayment()");
        System.out.println(amount+" "+paymentRequest);
        System.err.println(paymentRepository.findByUserId(userId));
        if (amount < 200) { // RM 2 minimum for MYR
            throw new IllegalArgumentException("Amount must be at least RM 2.00");
        }
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // Stripe takes smallest currency unit (₹1 = 100 paise)
                .setCurrency("myr")
                .addPaymentMethodType("card")
                .addPaymentMethodType("fpx")
                .addPaymentMethodType("grabpay")
                .setReceiptEmail(String.valueOf(paymentRequest.getUserInfo().getEmail()))
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Payment payment=dataConverter.convertToPayment(paymentIntent,paymentRequest);

        Payment existPayment=null;
        try {
            existPayment =paymentRepository.findByUserId(userId);
        } catch (Exception e) {
            e.getStackTrace();
        }
        UserInfo userInfo=paymentRequest.getUserInfo();

        PaymentDetails paymentDetails=PaymentDetails.builder().status(paymentIntent.getStatus()).userInfo(userInfo).Id(paymentIntent.getId()).
                                    amount(paymentRequest.getAmount()).paymentMethod(paymentIntent.getPaymentMethod()).build();

        if(paymentIntent.getClientSecret()!=null){
            System.err.println(paymentIntent.getClientSecret());
            if(existPayment == null || existPayment.getPaymentDetailsMap() == null || !existPayment.getUserId().equals(userId)){
                payment.setFirstName(paymentRequest.getFirstName());
                payment.setLastName((paymentRequest.getLastName()));
                payment.setUserId(userId);
                payment.setName(paymentRequest.getFirstName()+" "+paymentRequest.getLastName());
                payment.setPaymentDetailsMap(new HashMap<>());
                payment.getPaymentDetailsMap().put(paymentIntent.getClientSecret(),paymentDetails);
                payment=paymentRepository.save(payment);
            }else{
            existPayment.getPaymentDetailsMap().put(paymentIntent.getClientSecret(), paymentDetails);
            payment=paymentRepository.save(existPayment);
            }
        }
        else{
            throw new CustomRuntimeException("Client secret not created");
        }
        return dataConverter.convertToPaymentResponse(payment);
    } 

    public PaymentResponse verifyPayment(String paymentDetailsId) throws StripeException{
        Stripe.apiKey= secretKey;
        PaymentIntent paymentIntent=PaymentIntent.retrieve(paymentDetailsId);
        System.err.println(paymentIntent);
        Payment payment=paymentRepository.findByPaymentDetailsMap(paymentIntent.getClientSecret());
        PaymentDetails paymentDetails=payment.getPaymentDetailsMap().get(paymentIntent.getClientSecret());
        paymentDetails.setId(paymentIntent.getId());
        paymentDetails.setLatest_charge(paymentIntent.getLatestCharge());
        paymentDetails.setPaymentMethod(paymentIntent.getPaymentMethod());
        paymentDetails.setStatus(paymentIntent.getStatus());
        payment.getPaymentDetailsMap().put(paymentIntent.getClientSecret(), paymentDetails);
        System.err.println(payment);
        return dataConverter.convertToPaymentResponse(payment);
    }
}
