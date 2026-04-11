package AutoMobile.Cars.Controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Model.Payment;
import AutoMobile.Cars.Repository.PaymentRepository;
import AutoMobile.Cars.Service.PaymentService;
import AutoMobile.Cars.Util.DataConverter;
import AutoMobile.Cars.Util.payment.PaymentDetails;
import AutoMobile.Cars.Util.payment.PaymentRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
@Slf4j
public class PaymentController {

    PaymentService paymentService;
    PaymentRepository paymentRepository;
    DataConverter dataConverter;

    public PaymentController(PaymentService paymentService, PaymentRepository paymentRepository,
            DataConverter dataConverter) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
        this.dataConverter = dataConverter;
    }

    @PostMapping("/create-payment")
    public String createPayment(@RequestBody PaymentRequest paymentRequest) throws Exception { 
        log.info("paymentRequest : {}",paymentRequest);
        return paymentService.createPayment(paymentRequest);
    }

    @GetMapping("/verify-payment/{paymentIntentId}")
    public PaymentDetails verifyPayment(@PathVariable String paymentIntentId) throws Exception { 
        log.info("paymentIntentId : {}",paymentIntentId);
        return paymentService.verifyPayment(paymentIntentId);
    }

    @GetMapping("")
    public Payment get() throws CustomException{
        log.info("getPayment");
        try {
            UUID userId=dataConverter.getCurrentUserId();
        return paymentRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new CustomException("getPayemt", e);
        }
    }
}
