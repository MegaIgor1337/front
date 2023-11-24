package forwarding.agent.api.exceptions;

public class UserAlreadyConfirmedException extends RuntimeException {
    public UserAlreadyConfirmedException(String message) {
        super(message);
    }
}
