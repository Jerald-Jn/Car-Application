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
@CrossOrigin("*")
public class TotalCarsController {

    @Autowired
    TotalCarService service;

    @PostMapping("/add")
    public ResponseEntity<?> get(@RequestPart("cars") String jsonStringcar, @RequestPart("files")List<MultipartFile> images,
                        @RequestPart("banner") MultipartFile carImage,@RequestPart("logo") MultipartFile carLogo)
            throws CustomException {
        try {
            System.out.println("TotalCarsController.get()");
            return new ResponseEntity<>(service.addCar(jsonStringcar, images, carImage,carLogo ), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/get/{value}")
    public ResponseEntity<?> get(@PathVariable String value) throws CustomException {
        try {
            System.out.println("TotalCarsController.get()");
            List<CarResponse> response=service.getCar(value);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getListOfCars() throws CustomException{
        try {
            System.out.println("TotalCarsController.getListOfCars()");
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
