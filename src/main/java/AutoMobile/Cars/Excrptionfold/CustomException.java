package AutoMobile.Cars.Excrptionfold;

public class CustomException extends Exception {

    private String message;

    public CustomException(String message,Exception e) {
        super(message);
        this.message=message;
    }

    public CustomException(String message) {
        super(message);
        this.message=message;
    }

    public CustomException(Exception e) {
        super(e);
    }

}
