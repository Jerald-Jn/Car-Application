package AutoMobile.Cars.Excrptionfold;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomRuntimeException extends RuntimeException  {

    String message;

    public CustomRuntimeException(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public CustomRuntimeException(UsernameNotFoundException e) {
        // super(e);
        this.message=e.getMessage();
    }

    public CustomRuntimeException(Exception e) {
        super(e);
    }

}
