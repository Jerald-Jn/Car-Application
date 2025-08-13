package AutoMobile.Cars.Util;

import org.springframework.stereotype.Component;

import AutoMobile.Cars.Model.Cars;
import AutoMobile.Cars.Model.Cart;
import AutoMobile.Cars.Util.car.CarResponse;
import AutoMobile.Cars.Util.cart.CartResponse;

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
}
