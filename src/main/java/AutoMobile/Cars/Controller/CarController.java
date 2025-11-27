package AutoMobile.Cars.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Model.Cars;
import AutoMobile.Cars.Service.CarService;
import AutoMobile.Cars.Util.car.CarResponse;

@RestController
@RequestMapping("/cars")
@CrossOrigin("*")
public class CarController {

    @Autowired
    CarService service;

    @GetMapping("/get/{value}")
    public ResponseEntity<?> get(@PathVariable String value) throws CustomException {
        try {
            System.out.println("TotalCarsController.get()");
            List<CarResponse> response = service.getCar(value);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getListOfCars() throws CustomException {
        try {
            System.out.println("TotalCarsController.getListOfCars()");
            List<Cars> totalCars = service.getListOfCars();
            if (totalCars != null) {
                return ResponseEntity.ok().body(totalCars);
            }
            return ResponseEntity.badRequest().body("Api not running or error");
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

}
