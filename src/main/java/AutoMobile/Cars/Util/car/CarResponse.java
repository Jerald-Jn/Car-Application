package AutoMobile.Cars.Util.car;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private String model;
    private UUID carId;
    private String make;
    private int year;
    private String transmission;
    private Double engineCapacity;
    private Double mileage;
    private String vin;
    private String fuelType;
    private double price;
    private String color;
    private Object carImage;
    private Object carLogo;
}
