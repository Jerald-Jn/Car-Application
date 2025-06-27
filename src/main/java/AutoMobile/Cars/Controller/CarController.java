package AutoMobile.Cars.Controller;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.User;
import AutoMobile.Cars.Service.CarService;

@RestController
@RequestMapping("/car")
public class CarController {

	Logger log=LoggerFactory.getLogger(CarController.class);

	@Value("${token.file}")
	String fileName;

	CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping()
	public ResponseEntity<?> get() throws Exception {
		try {
			return new ResponseEntity<>(carService.getAll(), HttpStatus.OK);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException("Runtime Error -> " + r);
		} catch (Exception e) {
			throw new CustomException("error -> " + e);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
		try {
			log.info("user : {}",user);
			UUID uuid = UUID.randomUUID();
			return new ResponseEntity<>(carService.addUser(user, uuid), HttpStatus.ACCEPTED);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException("Runtime Error -> " + r);
		} catch (Exception e) {
			throw new CustomException("error -> " + e);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getByID(@RequestParam(required = true) String userName) throws Exception {
		try {
			log.info("find userName : {}",userName);
			return new ResponseEntity<>(carService.getByID(userName), HttpStatus.OK);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException(String.format("error ->" + r + "Unable to find the file : %s", fileName));
		} catch (Exception e) {
			throw new CustomException(String.format("error -> " + e));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam(required = true) String userName) throws Exception {
		try {
			log.info(" delete userName : {}", userName);
			return new ResponseEntity<>(carService.delete(userName), HttpStatus.ACCEPTED);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException("Runtime Error -> " + r);
		} catch (Exception e) {
			throw new CustomException("error -> " + e);
		}

	}

}
