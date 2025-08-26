package AutoMobile.Cars.Excrptionfold;

public class CustomException extends Exception {

    public CustomException(String format) {
        super(format);
    }

    public CustomException(Exception e) {
        super(e);
    }

}
