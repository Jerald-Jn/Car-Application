package AutoMobile.Cars.Auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AutoMobile.Cars.Repository.UserRepo;

@RestController
@RequestMapping()
public class UserController {

    UserService service;

    @Autowired
    UserRepo userRepo;

    public UserController(UserService service){
        this.service=service;
    }
    
    @GetMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String password){
        return service.login(userName,password);
    }

    @GetMapping("/")
    public Temp get(@RequestBody Temp temp){
        return temp;
    }

    @GetMapping("/{firstName}")
    public String g(@PathVariable String firstName){
        return "hello "+firstName;
    }
}

class Temp {
    private String firstName;
    private String lastName;
    private String email;
    String guid;

    // Constructors
    public Temp() {}
    public Temp(String firstName, String lastName, String email,String guid) {
        this.firstName = firstName;
        this.lastName=lastName;
        this.email=email;
        this.guid=guid;
    }

    // Getter & Setter
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLastName() {
        return lastName;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }
    public String getEmail() {
        return email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getGuid() {
        return guid;
    }
}