package AutoMobile.Cars.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AutoMobile.Cars.Model.Cars;

@Repository
public interface CarsRepository extends MongoRepository<Cars,String> {
    Cars findByFuelType(String fuelType);
}
