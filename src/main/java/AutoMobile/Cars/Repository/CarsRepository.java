package AutoMobile.Cars.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import AutoMobile.Cars.Model.Cars;

@Repository
public interface CarsRepository extends MongoRepository<Cars,String> {
    List<Cars> findByFuelType(String fuelType);
    List<Cars> findByTransmission(String transmission);
}
