package AutoMobile.Cars.Controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserMainController {

	UserMainService userMainService;
    JwtBlacklist jwtBlacklist;

	public UserMainController(UserMainService userMainService, JwtBlacklist jwtBlacklist) {
		this.userMainService = userMainService;
		this.jwtBlacklist = jwtBlacklist;
	}

	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
		try {
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
	public ResponseEntity<?> getByUserName(@RequestParam(required = true) String userName) throws Exception {
		try {
			log.info("find userName : {}",userName);
			return new ResponseEntity<>(userMainService.getByID(userName), HttpStatus.OK);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException(String.format("error ->" + r));
		} catch (Exception e) {
			throw new CustomException(String.format("error -> " + e));
		}
	}

}
