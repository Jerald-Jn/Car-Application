package AutoMobile.Cars.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.Payment;
import AutoMobile.Cars.Model.UserInfo;
import AutoMobile.Cars.Repository.PaymentRepository;
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

    public String createPayment(PaymentRequest paymentRequest) throws StripeException {

        Stripe.apiKey = secretKey;

        UUID userId = dataConverter.getCurrentUserId();
        long amount = Math.round(paymentRequest.getAmount());
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

        if (paymentIntent.getClientSecret() != null) {
            try {
                UserInfo userInfo = paymentRequest.getUserInfo();
                PaymentDetails paymentDetails = PaymentDetails.builder().status(paymentIntent.getStatus())
                        .userInfo(userInfo).Id(paymentIntent.getId()).amount(paymentIntent.getAmount())
                        .paymentMethod(paymentIntent.getPaymentMethod()).build();
                Payment existPayment = paymentRepository.findByUserId(userId);
                if (existPayment == null || existPayment.getPaymentDetailsMap() == null
                        || !existPayment.getUserId().equals(userId)) {
                    Payment payment = dataConverter.convertToPayment(paymentIntent, paymentRequest);
                    payment.setUserId(userId);
                    payment.getPaymentDetailsMap().put(paymentIntent.getClientSecret(), paymentDetails);
                    payment = paymentRepository.save(payment);
                } else {
                    existPayment.getPaymentDetailsMap().put(paymentIntent.getClientSecret(), paymentDetails);
                    existPayment = paymentRepository.save(existPayment);
                }
            } catch (Exception e) {
                throw new CustomRuntimeException("Client secret not created");
            }
        }
        return paymentIntent.getClientSecret();
    }

    public PaymentResponse verifyPayment(Object clientSecret) throws StripeException {

        Stripe.apiKey = secretKey;
        UUID userId = dataConverter.getCurrentUserId();
        Payment payment=paymentRepository.findByUserId(userId);
        PaymentIntent paymentIntent = PaymentIntent.retrieve(payment.getPaymentDetailsMap().get(clientSecret).getId());
        PaymentDetails paymentDetails = payment.getPaymentDetailsMap().get(paymentIntent.getClientSecret());
        paymentDetails.setId(paymentIntent.getId());
        paymentDetails.setLatest_charge(paymentIntent.getLatestCharge());
        PaymentMethod method = PaymentMethod.retrieve(paymentIntent.getPaymentMethod());
        paymentDetails.setPaymentMethod(method.getType());
        paymentDetails.setStatus(paymentIntent.getStatus());
        payment.getPaymentDetailsMap().put(paymentIntent.getClientSecret(), paymentDetails);
        paymentRepository.save(payment);
        return dataConverter.convertToPaymentResponse(payment);
    }

}
