package AutoMobile.Cars.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Model.Payment;
import AutoMobile.Cars.Repository.PaymentRepository;
import AutoMobile.Cars.Service.PaymentService;
import AutoMobile.Cars.Util.DataConverter;
import AutoMobile.Cars.Util.payment.PaymentRequest;
import AutoMobile.Cars.Util.payment.PaymentResponse;

@RestController
@RequestMapping("/payments")
@CrossOrigin()
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    DataConverter dataConverter;

    @PostMapping("/create-payment")
    public PaymentResponse createPayment(@RequestBody PaymentRequest paymentRequest) throws Exception { 
        return paymentService.createPayment(paymentRequest);
    }

    @GetMapping("/verify-payment/{paymentDetailsId}")
    public PaymentResponse verifyPayment(@PathVariable String paymentDetailsId) throws Exception { 
        System.err.println(paymentDetailsId);        
        return paymentService.verifyPayment(paymentDetailsId);
    }

    @GetMapping()
    public Payment get(){
        UUID userId=dataConverter.getCurrentUserId();
        return paymentRepository.findByUserId(userId);
    }
}
