package AutoMobile.Cars.Util.car;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CarRequest {
    private String model;
    private String make;
    private int year;
    private String transmission;
    private Double engineCapacity;
    private Double mileage;
    private String vin;
    private String fuelType;
    private double price;
    private String color;
    private byte[] carImage;
    private byte[] carLogo;
}
