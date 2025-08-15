package AutoMobile.Cars.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Model.Cars;
import AutoMobile.Cars.Repository.CarsRepository;
import AutoMobile.Cars.Util.DataConverter;
import AutoMobile.Cars.Util.car.CarResponse;
import AutoMobile.Cars.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TotalCarService {

    CarsRepository repository;
    CarResponse response;
    CloudinaryService cloudinaryService;

    Map<String,List<Cars>> carMap=new HashMap<>();

    public Cars addCar(String jsoncars, MultipartFile carImage,MultipartFile carLogo) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Cars cars = objectMapper.readValue(jsoncars, Cars.class);
            cars.setCarImage(cloudinaryService.uploadImage(carImage));
            cars.setCarLogo(cloudinaryService.uploadImage(carLogo));
            cars.setCarId(UUID.randomUUID());
            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.IMAGE_JPEG);
            return repository.save(cars);
        } catch (Exception e) {
            throw new CustomException("error in addcar method");
        }
    }

    public List<CarResponse> getCar(String model) throws CustomException{
        List<CarResponse> responses = new ArrayList<>();
        try {
            if(model!=null){
                if(model.equalsIgnoreCase("petrol") || model.equalsIgnoreCase("diesel") || model.equalsIgnoreCase("e-cvt")){
                    model=model.toLowerCase();
                    List<Cars> carEntity=repository.findByFuelType(model);
                    for (Cars carResponse : carEntity) {
                        responses.add(DataConverter.carToCarResponse(carResponse));
                    }
                }else
                if( model.equalsIgnoreCase("Auto") || model.equalsIgnoreCase("Manual") || model.equalsIgnoreCase("Hybrid")){
                    model=model.toLowerCase();
                    List<Cars> carEntity=repository.findByTransmission(model);
                    for (Cars carResponse : carEntity) {
                        responses.add(DataConverter.carToCarResponse(carResponse));
                    }
                }
                Optional<Cars> option=repository.findById(model);
                if(option.isPresent()){
                    responses.add(DataConverter.carToCarResponse(option.get()));
                }
                return responses;
            }  
        } catch (Exception e) {
            throw new CustomException("error in getcar method");
        }
        return null;
    }

    public Object getImage(String model) throws CustomException{
        try {
            Optional<Cars> car=repository.findById(model);
            // String image=Base64.getEncoder().encodeToString(car.getCarImage());
            if(car.isPresent()){
                return car.get().getCarImage();
            }
            return null;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public List<Cars> getListOfCars() throws CustomException {
        try {
            List<Cars> lisOfCars=repository.findAll();
            if(lisOfCars!=null){
                // for(int i=0;i<lisOfCars.size();i++){
                //     System.out.println(lisOfCars.get(i).getCarImage());
                // }
                return lisOfCars;
            }
        } catch (Exception e) {
            throw new CustomException("error in getcar method");
        }
        return null;
    }
}
