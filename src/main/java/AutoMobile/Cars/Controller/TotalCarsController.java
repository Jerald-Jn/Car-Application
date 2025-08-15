package AutoMobile.Cars.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Model.Cars;
import AutoMobile.Cars.Service.TotalCarService;
import AutoMobile.Cars.Util.car.CarResponse;

@Controller
@RequestMapping("/cars")
@CrossOrigin
public class TotalCarsController {

    @Autowired
    TotalCarService service;

    @PostMapping("/add")
    public ResponseEntity<?> get(@RequestPart("cars") String jsonStringcar, @RequestPart("image") MultipartFile carImage,@RequestPart("logo") MultipartFile carLogo)
            throws CustomException {
        try {
            return new ResponseEntity<>(service.addCar(jsonStringcar, carImage,carLogo), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/get/{value}")
    public ResponseEntity<?> get(@PathVariable String value) throws CustomException {
        try {
            List<CarResponse> response=service.getCar(value);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/getImage/{value}")
    public ResponseEntity<?> getImage(@PathVariable String value) throws CustomException {
        try {
            // byte[] image=service.getImage(value);
            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.IMAGE_JPEG);
            // return new ResponseEntity<>(image,headers, HttpStatus.ACCEPTED);
            Object image=service.getImage(value);
            return ResponseEntity.ok().body(image);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }



    @GetMapping("")
    public ResponseEntity<?> getListOfCars() throws CustomException{
        try {
            List<Cars> totalCars=service.getListOfCars();
            if(totalCars!=null){
                return ResponseEntity.ok().body(totalCars);
            }
            return ResponseEntity.badRequest().body("Api not running or error");
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

}
