package AutoMobile.Cars.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Model.Cars;
import AutoMobile.Cars.Service.TotalCarService;

@Controller
@RequestMapping("/cars")
public class TotalCarsController {

    @Autowired
    TotalCarService service;
    
@PostMapping("/add")
public ResponseEntity<?> get(@RequestPart("cars") String jsoncars, @RequestPart("image") MultipartFile image) throws CustomException {
    try {
        return new ResponseEntity<>( service.addCar(jsoncars,image), HttpStatus.ACCEPTED);
    } catch (Exception e) {
        throw new CustomException(e);
    }
}

@GetMapping("/get/{model}/{color}")
public ResponseEntity<?> get(@PathVariable String model,@PathVariable String color) throws CustomException{
    try {
        Cars car=service.getCars(model,color);
        if(car==null){
            return new ResponseEntity<>("that special car not available", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(car, HttpStatus.ACCEPTED);
    } catch (CustomException e) {
        throw new CustomException(e);
    }
}

}
