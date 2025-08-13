package AutoMobile.Cars.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "User-Car-DB")
public class Car {
	private String carId;
	@Id
	private String make;
	private int year;
	private String transmission;
	private Double engineCapacity;
	private Double mileage;
	private String registrationNumber;
	private String vin;
	private String model;
	private String fuelType;
	private double price;
	private String color;
	private Insurance insurance;

	@Override
	public String toString() {
		return "Car [carId=" + carId + ", make=" + make + ", year=" + year + ", transmission=" + transmission
				+ ", engineCapacity=" + engineCapacity + ", mileage=" + mileage + ", registrationNumber="
				+ registrationNumber + ", vin=" + vin + ", model=" + model + ", fuelType=" + fuelType + ", price="
				+ price + ", color=" + color + ", insurance=" + insurance;
	}
}
