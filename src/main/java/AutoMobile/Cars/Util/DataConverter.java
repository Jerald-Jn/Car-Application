package AutoMobile.Cars.Util;

import org.springframework.stereotype.Component;

import com.stripe.model.PaymentIntent;

import AutoMobile.Cars.Model.Cars;
import AutoMobile.Cars.Model.Cart;
import AutoMobile.Cars.Model.Order;
import AutoMobile.Cars.Util.car.CarResponse;
import AutoMobile.Cars.Util.cart.CartResponse;
import AutoMobile.Cars.Util.order.OrderRequest;
import AutoMobile.Cars.Util.order.OrderResponse;

@Component
public class DataConverter {
    
    public static CarResponse carToCarResponse(Cars carEntity){
        return CarResponse.builder().carId(carEntity.getCarId()).model(carEntity.getModel())
        .color(carEntity.getColor()).engineCapacity(carEntity.getEngineCapacity()).fuelType(carEntity.getFuelType()).make(carEntity.getMake())
        .mileage(carEntity.getMileage()).price(carEntity.getPrice()).transmission(carEntity.getTransmission()).vin(carEntity.getVin())
        .year(carEntity.getYear()).carLogo(carEntity.getCarLogo()).carImage(carEntity.getCarImage()).build();
    }

    public static CartResponse convertToCartResponse(Cart cart){
        return CartResponse.builder().id(cart.getId()).user(cart.getUser()).items(cart.getItems()).build();
    }

    public Order convertTOrder(PaymentIntent paymentIntent, OrderRequest orderRequest) {
        return Order.builder()
                            .clientSecret(paymentIntent.getClientSecret()).first_name(orderRequest.getFirst_name()).last_name(orderRequest.getLast_name())
                            .paymentMethod(paymentIntent.getPaymentMethod()).userInfo(orderRequest.getUserInfo()).amount(orderRequest.getAmount()).build();

    }

    public OrderResponse convertTOrderResponse(Order order) {
        return OrderResponse.builder()
                            .clientSecret(order.getClientSecret()).first_name(order.getFirst_name()).last_name(order.getLast_name())
                            .transactionId(order.getTransactionId())
                            .paymentMethod(order.getPaymentMethod()).userInfo(order.getUserInfo()).amount(order.getAmount()).build();

    }


}

   