package AutoMobile.Cars.Excrptionfold;

public class CustomException extends Exception {

    public CustomException(String message,Exception e) {
        super(message,e);
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(Exception e) {
        super(e);
    }

}
