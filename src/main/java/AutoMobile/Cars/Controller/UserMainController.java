package AutoMobile.Cars.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.cloudinary.Cloudinary;

import AutoMobile.Cars.Excrptionfold.CustomException;
import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.User;
import AutoMobile.Cars.Security.JwtBlacklist;
// import jakarta.servlet.http.HttpServletRequest;
import AutoMobile.Cars.Service.UserMainService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserMainController {

	Logger log=LoggerFactory.getLogger(UserMainController.class);

	@Value("${token.file}")
	String fileName;

	UserMainService userMainService;
	@Autowired
    JwtBlacklist jwtBlacklist;

	public UserMainController(UserMainService userMainService) {
		this.userMainService = userMainService;
	}

	@GetMapping()
	public ResponseEntity<?> get() throws Exception {
		try {
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
			log.info("find userName : {}",userName);
			return new ResponseEntity<>(userMainService.getByID(userName), HttpStatus.OK);
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
			return new ResponseEntity<>(userMainService.delete(userName), HttpStatus.ACCEPTED);
		} catch (RuntimeException r) {
			throw new CustomRuntimeException("Runtime Error -> " + r);
		} catch (Exception e) {
			throw new CustomException("error -> " + e);
		}

	}

	@Autowired
	Cloudinary cloudinary;

	@GetMapping("/alive")
	public ResponseEntity<?> add(){
		Map<?,?> map = new HashMap<>();
		try {
			map=cloudinary.api().resource("bvsllqk51c8hh2sjdlzo",null);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return new ResponseEntity<>(map,HttpStatus.ACCEPTED);
	}

}
