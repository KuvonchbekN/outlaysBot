package by.kuvonchbekn.outlaysbot.exception.specificExceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
