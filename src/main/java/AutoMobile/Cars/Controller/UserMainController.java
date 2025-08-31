package AutoMobile.Cars.Controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import AutoMobile.Cars.Security.JwtBlacklist;
import AutoMobile.Cars.Service.UserMainService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserMainController {

	Logger log=LoggerFactory.getLogger(UserMainController.class);

	UserMainService userMainService;
	@Autowired
    JwtBlacklist jwtBlacklist;

	public UserMainController(UserMainService userMainService) {
		this.userMainService = userMainService;
	}

	@GetMapping()
	public ResponseEntity<?> get() throws Exception {
		try {
			System.out.println("UserMainController.get()");
			return new ResponseEntity<>(userMainService.getAll(), HttpStatus.OK);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException("Runtime Error -> " + r);
		} catch (Exception e) {
			throw new CustomException("error -> " + e);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
		try {
			System.out.println("UserMainController.addUser()");
			log.info("user : {}",user);
			UUID uuid = UUID.randomUUID();
			return new ResponseEntity<>(userMainService.addUser(user, uuid), HttpStatus.ACCEPTED);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException("Runtime Error -> " + r);
		} catch (Exception e) {
			throw new CustomException("error -> " + e);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getByID(@RequestParam(required = true) String userName) throws Exception {
		try {
			System.out.println("UserMainController.getByID()");
			log.info("find userName : {}",userName);
			return new ResponseEntity<>(userMainService.getByID(userName), HttpStatus.OK);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException(String.format("error ->" + r));
		} catch (Exception e) {
			throw new CustomException(String.format("error -> " + e));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam(required = true) String userName) throws Exception {
		try {
			System.out.println("UserMainController.delete()");
			log.info(" delete userName : {}", userName);
			return new ResponseEntity<>(userMainService.delete(userName), HttpStatus.ACCEPTED);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException("Runtime Error -> " + r);
		} catch (Exception e) {
			throw new CustomException("error -> " + e);
		}

	}

}
