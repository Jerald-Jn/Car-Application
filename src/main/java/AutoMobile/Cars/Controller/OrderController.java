package AutoMobile.Cars.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Service.OrderService;
import AutoMobile.Cars.Util.order.OrderRequest;
import AutoMobile.Cars.Util.order.OrderResponse;

@RestController
@RequestMapping("/payments")
@CrossOrigin()
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create-payment")
    public OrderResponse createPayment(@RequestBody OrderRequest orderRequest) throws Exception { 
        return orderService.createPayment(orderRequest);
    }
}
