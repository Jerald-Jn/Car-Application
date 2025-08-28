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
import AutoMobile.Cars.Util.payment.PaymentDetails;
import AutoMobile.Cars.Util.payment.PaymentRequest;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    DataConverter dataConverter;

    @PostMapping("/create-payment")
    public String createPayment(@RequestBody PaymentRequest paymentRequest) throws Exception { 
        System.out.println("PaymentController.createPayment()");
        return paymentService.createPayment(paymentRequest);
    }

    @GetMapping("/verify-payment/{paymentIntentId}")
    public PaymentDetails verifyPayment(@PathVariable String paymentIntentId) throws Exception { 
        System.out.println("PaymentController.verifyPayment()");
        return paymentService.verifyPayment(paymentIntentId);
    }

    @GetMapping()
    public Payment get(){
        System.out.println("PaymentController.get()");
        UUID userId=dataConverter.getCurrentUserId();
        return paymentRepository.findByUserId(userId);
    }
}
