package AutoMobile.Cars.Excrptionfold;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomRuntimeException extends RuntimeException {

    public CustomRuntimeException(String format) {
        super(format);
    }

    public CustomRuntimeException(UsernameNotFoundException e) {
        super(e);
    }

    public CustomRuntimeException(Exception e) {
        super(e);
    }

}
