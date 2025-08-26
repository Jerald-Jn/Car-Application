package AutoMobile.Cars.Model;

import java.util.Map;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "Cars-Table-2")
public class Cars {
    @Id
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
    private Map<String,Object> images;

    @Override
    public String toString() {
        return "Cars [model=" + model + ", carId=" + carId + ", make=" + make + ", year=" + year + ", transmission="
                + transmission + ", engineCapacity=" + engineCapacity + ", mileage=" + mileage + ", vin=" + vin
                + ", fuelType=" + fuelType + ", price=" + price + ", color=" + color + ", carImage=" + carImage
                + ", carLogo=" + carLogo + ", images=" + images + "]";
    }

}
