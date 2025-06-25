package AutoMobile.Cars.Excrptionfold;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AllCustomExceptionHandler {

    Logger log=LoggerFactory.getLogger(AllCustomExceptionHandler.class);

    List<String> errorList = new ArrayList<>();

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> CustomExceptionHandler(Exception e, WebRequest request) {
        errorList.add(e.getMessage());
        log.info("CustomException -> :{} ", e.getMessage());
        // System.out.println("CustomException -> " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<?> CustomRumtimeExceptionHandler(Exception e, WebRequest request) {
        errorList.add(e.getMessage());
        log.info("CustomRuntimeException -> :{} ", e.getMessage());
        // System.err.println("CustomRuntimeException -> " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
    }
}
