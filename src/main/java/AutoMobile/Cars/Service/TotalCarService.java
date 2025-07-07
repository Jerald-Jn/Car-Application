package AutoMobile.Cars.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Model.Cars;
import AutoMobile.Cars.Repository.CarsRepository;

@Service
public class TotalCarService {

    @Autowired
    CarsRepository repository;

    public Cars addCar(String jsoncars, MultipartFile image) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Cars cars = objectMapper.readValue(jsoncars, Cars.class);
            cars.setImage(image.getBytes());
            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.IMAGE_JPEG);
            return repository.save(cars);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public Cars getCars(String model,String color) throws CustomException{
        try {
            Cars car=null;
            if(model!=null && color!=null){
                car=repository.findById(model).get();
                if(car.getColor().equals(color)){
                    return car;
                }
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return null;
    }
}
